from curl_cffi import requests
from bs4 import BeautifulSoup
import pandas as pd
import time
import random


def define_links(parsed_html, pathern):
    link_list = parsed_html.find_all('a')
    linked_list_fin = []
    for link in link_list:
        a = link.get('href')
        if type(a) is str:
            if a.startswith(pathern) and a != pathern:
                linked_list_fin.append(a)
    return linked_list_fin


def extract_info(link_list):
    final_dict = {}
    for info in link_list:
        try:
            index = info.find('p', attrs={'class':'mb-2'}).text.strip()
            info_list = info.find('p', attrs={'class':'mb-0'}).text
            final_info_list = []
            for i in info_list.split(","):
                final_info_list.append(i.strip())
        except:
            index = info.find('div', attrs={'class':'mb-2'}).text.strip()
            info_list = info.find('div', attrs={'class':'mb-0'}).text.strip()
            final_info_list = [info_list]
        final_dict[index] = ";".join(final_info_list)
    return final_dict


def make_request(link):
    time.sleep(1)
    proxies_list = [
        '111.111.111.111:2222',
        '333.333.333.333:4444',
        '444.444.444.444:5555',
        '777.777.777.777:8888',
        '919.919.919.919:0000'
    ]

    proxies = {
      'http': random.choice(proxies_list)
    }
    response = requests.get(
        link,
        impersonate="chrome",
        proxies=proxies
    )
    html = None
    if response.status_code != 200:
        print(f"An error occured with status {response.status_code}")
    else:
        html = response.text
    "fetch done"
    return BeautifulSoup(html, "html.parser")
