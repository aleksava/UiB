
# FINDS LOCATION OF IP AND PRINTS TO SCREEN
import requests
import json

send_url = 'http://freegeoip.net/json'
r = requests.get(send_url)
j = json.loads(r.text)
lat = j['latitude']
lon = j['longitude']
country = j['country_name']
city = j['city']
#zip_code = j['zipcode']
print("Country: " + country)
#print("Zip code: " + zipcode)
print("City: " + city)
print("Latitude: ", lat)
print("Longitude: ", lon)


# SENDS EMAIL OF LOCATION TO DESIGNATED EMAIL

# Import smtplib for the actual sending function
import smtplib

# Import the email modules we'll need
from email.mime.text import MIMEText

# Open a plain text file for reading.  For this example, assume that
# the text file contains only ASCII characters.
fp = open(textfile, 'rb')
# Create a text/plain message
msg = MIMEText(fp.read())
fp.close()

# me == the sender's email address
# you == the recipient's email address
msg['Subject'] = 'The contents of %s' % textfile
msg['From'] = Aleksander@vage.com
msg['To'] = Alek@vage.com

# Send the message via our own SMTP server, but don't include the
# envelope header.
s = smtplib.SMTP('localhost')
s.sendmail(me, [you], msg.as_string())
s.quit()
