#!/usr/bin/env python
# coding: utf-8

# ### Automated Instagram Bot
# - Version 1 <br>
# - Future Goals: <br>
#     Use ApacheSpark or Python's Multiprocessing library to be able to run the automated process on multiple accounts in parallel. <br> 
#     Fix minor bugs.

# In[378]:


import csv
from bs4 import BeautifulSoup
from selenium import webdriver
from time import sleep


# In[375]:


class instaBot:
    def __init__(self, username, password, target_profile):
        """
        Creates an instance of the instaBot class.
        
        Args:
            username:str: The username of the user.
            password:str: The password of the user.
            target_profile:str: The username data will be pulled from.
        
        Attributes:
            driver:str: Instance of Selenium webdriver (Chrome).
            base_url:str: The URL for webpage.
        """
        self.username = username
        self.password = password
        self.target_profile = target_profile
        self.driver = webdriver.Chrome(executable_path='/Users/danielmartin/Desktop/chromedriver')
        self.base_url = 'https://www.instagram.com/'
        
        self.driver.get(self.base_url)
        sleep(3)
        
        self.login()
        sleep(4)
        self.close_popup()
        sleep(3)
        self.get_profile()
        sleep(3)
        self.data = self.collect_all_post_data()
        sleep(3)
        self.download_post_data(self.data)
        self.driver.quit()
    
    def login(self):
        """
        Login user through webpage.
        """
        username = self.driver.find_element_by_xpath('//input[@name=\"username\"]')
        password = self.driver.find_element_by_xpath('//input[@name=\"password\"]')
        login = self.driver.find_element_by_xpath('//button[@type="submit"]')
            
        username.send_keys(self.username)
        password.send_keys(self.password)
        login.click()
            
    def close_popup(self):
        """
        Bypass dialog boxes - 'Save Login/Turn on Notifications'.
        """
        self.driver.find_element_by_xpath("//button[contains(text(), 'Not Now')]").click()
        self.driver.find_element_by_xpath("//button[contains(text(), 'Not Now')]").click()
        
    def get_profile(self):
        """
        Navigate to target profile.
        """
        profile_url = self.base_url+self.target_profile
        self.driver.get(profile_url)
                                                                   
    def click_post(self):
        """
        Click on single instagram post.
        """
        self.driver.find_element_by_xpath("//a[contains(@href, '/p')]").click()
    
    def get_number_likes(self):
        """
        Collect the total number of likes for a single post.
        """
        try:
            xpath = "/html/body/div[4]/div[2]/div/article/div[2]/section[2]/div/div[2]/button/span"
            total_num_likes = int(self.driver.find_element_by_xpath(xpath).text)+1
            #print(total_num_likes)
            return total_num_likes
        except Exception:
            return 'No Like (Views)'
    
    def get_datetime(self):
        """
        Collect the date of when the post was uploaded.
        """
        xpath = "/html/body/div[4]/div[2]/div/article/div[2]/div[2]/a/time"
        datetime = self.driver.find_element_by_xpath(xpath).text
        #print(datetime)
        return datetime
        
    def get_image(self):
        """
        Collect the image of the post.
        """
        try:
            soup = BeautifulSoup(self.driver.page_source, "html.parser")
            img = soup.find('img', attrs = {'class': 'FFVAD'}).get("src")
            #print(img)
            return img
        except Exception:
            return 'Video URL'
    
    def get_location(self):
        """
        Collect the location of the post.
        """
        try:
            xpath = "/html/body/div[4]/div[2]/div/article/header/div[2]/div[2]/div[2]/a"
            loc = self.driver.find_element_by_xpath(xpath).text
            #print(loc)
            return loc
        except Exception:
            #print('No Location')
            return 'No location'
    
    def get_description(self):
        """
        Collect the description/caption of the post.
        """
        try:
            xpath = "/html/body/div[4]/div[2]/div/article/div[2]/div[1]/ul/div/li/div/div/div[2]/span"
            desc = self.driver.find_element_by_xpath(xpath).text
            #print(desc)
            return desc
        except Exception:
            #print('No Description')
            return 'No Description'
    
    def single_post_data(self):
        """
        Process of collecting data from a single post.
        """
        likes = self.get_number_likes()
        sleep(1)
        datetime = self.get_datetime()
        sleep(1)
        location = self.get_location()
        sleep(1)
        description = self.get_description()
        sleep(1)
        image = self.get_image()
        
        return [likes,datetime,location,description,image]
 
    def next_right_post(self):
        """
        Clicks the next right post.
        """
        right_arrow = self.driver.find_element_by_xpath("//a[contains(@class, 'coreSpriteRightPaginationArrow')]")
        right_arrow.click()
   
    def total_num_of_posts(self):
        """
        Returns the total number of posts a user has.
        """
        xpath = '//*[@id="react-root"]/section/main/div/header/section/ul/li[1]/span/span'
        self.num_of_posts = int(self.driver.find_element_by_xpath(xpath).text)
        return self.num_of_posts
    
    def collect_all_post_data(self):
        """
        Collects all data from all posts.
        """
        self.click_post()
        sleep(2)
        
        store_data = [self.single_post_data()]
        count, total_number_posts = 0, self.total_num_of_posts()
        while count < total_number_posts-1:
            self.next_right_post()
            sleep(1.5)
            store_data.append(self.single_post_data())
            sleep(1.5)
            count += 1
            
        #print(store_data)
        return store_data
        
    def download_post_data(self, data):
        """
        Outputs data collected to a csv.
        """
        data = [['Number of likes', 'Date', 'Location', 'Caption', 'Image URL']]+data
        with open(self.target_profile+'_data.csv', mode='w') as file:
            file_writer = csv.writer(file)
            file_writer.writerows(data)
    

