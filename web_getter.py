import requests
import csv
import re
import os
from bs4 import BeautifulSoup

BASE_URL = "https://theorangealliance.org"
match_number = 0
def get_match_links(url=f"{BASE_URL}/events/2425-TX-MIS"):
    # Fetch all unique match links from the event page
    response = requests.get(url)
    if response.status_code != 200:
        print(f"Failed to retrieve event page. Status code: {response.status_code}")
        return set()
    
    soup = BeautifulSoup(response.text, 'html.parser')
    match_links = set()

    for a_tag in soup.find_all("a", href=True):
        href = a_tag["href"]
        if "/matches/" in href and not href.endswith("#"):
            full_link = BASE_URL + href if href.startswith("/") else href
            match_links.add(full_link)

    print(f"Total matches found: {len(match_links)}")
    return match_links

def get_overall_scores(soup):
    # Extract overall red and blue scores
    red_score, blue_score = "", ""
    
    red_div = soup.find("div", class_="MuiGrid-root MuiGrid-item MuiGrid-grid-xs-3 css-1s3nz4t")
    if red_div:
        red_text = red_div.get_text(separator=" ", strip=True)
        red_score = red_text.replace("Red", "").strip()

    blue_div = soup.find("div", class_="MuiGrid-root MuiGrid-item MuiGrid-grid-xs-3 css-f06w6e")
    if blue_div:
        blue_text = blue_div.get_text(separator=" ", strip=True)
        blue_score = blue_text.replace("Blue", "").strip()

    return red_score, blue_score

def get_additional_stats(soup):
    # Extract additional stats like Samples, Teleop, Fouls
    stats = {}
    stat_rows = soup.find_all("div", class_="MuiGrid-root MuiGrid-container css-1jvovdg")
    
    for row in stat_rows:
        cells = row.find_all("div", recursive=False)
        if len(cells) < 3:
            continue  

        label_tag = cells[0].find("p")
        red_tag = cells[1].find("p")
        blue_tag = cells[2].find("p")

        if label_tag and red_tag and blue_tag:
            label = label_tag.get_text(strip=True)
            red_val = clean_numeric_value(red_tag.get_text(strip=True))
            blue_val = clean_numeric_value(blue_tag.get_text(strip=True))
            stats[label] = (red_val, blue_val)

    return stats

def clean_numeric_value(value):
    # Extract only numeric values
    match = re.search(r'\d+', value)
    return match.group(0) if match else "0"

def get_teams(soup):
    # Extract the team numbers for both alliances
    red_teams, blue_teams = "", ""

    table_rows = soup.find_all("tr", class_="MuiTableRow-root css-10dixy1")

    for row in table_rows:
        row_style = row.get("style", "").lower()
        is_red = "var(--toa-colors-red-transparent)" in row_style
        is_blue = "var(--toa-colors-blue-transparent)" in row_style

        team_links = row.find_all("a", href=True)
        team_numbers = [link.text.strip() for link in team_links if "/teams/" in link["href"]]

        if is_red:
            red_teams = ", ".join(team_numbers)
        elif is_blue:
            blue_teams = ", ".join(team_numbers)

    return red_teams, blue_teams

def get_match_data(match_url):
    global match_number
    # Extract match data and return two rows: one for Red, one for Blue
    response = requests.get(match_url)
    if response.status_code != 200:
        print(f"Failed to retrieve match page: {match_url}")
        return None

    soup = BeautifulSoup(response.text, 'html.parser')

    # Extract match number from URL
    match_number = int(match_number)
    match_number +=1
    match_number = str(match_number)

    # Get scores, stats, and teams
    red_score, blue_score = get_overall_scores(soup)
    red_teams, blue_teams = get_teams(soup)
    stats = get_additional_stats(soup)

    # Prepare data for two rows (one per alliance)
    red_data = {"match number": match_number, "url": match_url, "alliance": "Red", "teams": red_teams, "score": red_score}
    blue_data = {"match number": match_number, "url": match_url, "alliance": "Blue", "teams": blue_teams, "score": blue_score}

    # Add all stats
    for stat, values in stats.items():
        red_data[stat] = values[0]
        blue_data[stat] = values[1]

    return [red_data, blue_data]

def save_to_csv(data, filename="matches.csv"):
    # Save match data to a semicolon separated CSV file
    if '.csv' in filename:
        pass
    else:
        filename+='.csv'
    if not data:
        print("No data to save.")
        return
    
    fieldnames = ["match_number", "url", "alliance", "teams", "score"]  # Core fields
    extra_fields = sorted(set(k for match in data for k in match.keys() if k not in fieldnames))
    fieldnames.extend(extra_fields)  # Append dynamically extracted stats
    current_directory = os.path.abspath(os.curdir)
    filename = os.path.join(current_directory, filename)
    # Write to CSV using semicolon as delimiter
    with open(filename, "w", newline="", encoding="utf-8") as file:
        writer = csv.DictWriter(file, fieldnames=fieldnames, delimiter=';')
        writer.writeheader()
        writer.writerows(data)

    print(f"Data successfully saved to {filename} (semicolon-separated)")

def getter(link):
    # Scrape match data and save it to a CSV file
    match_links = get_match_links(url=link)
    all_match_data = []

    if not match_links:
        print("No match links found.")
        return

    for link in match_links:
        match_data = get_match_data(link)
        if match_data:
            all_match_data.extend(match_data)
    save_to_csv(all_match_data, filename=input('Enter file name to save match data to: '))
