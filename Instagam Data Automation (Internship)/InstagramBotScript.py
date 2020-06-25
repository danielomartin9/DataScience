from InstagramWebCrawler import *

username = input('Enter your Instagram username: ')
password = input('Enter your Instagram password: ')
target = input('Enter the target Instagram username: ')

# Outputs a CSV of data on desktop
instaBot = instaBot(username, password, target)
