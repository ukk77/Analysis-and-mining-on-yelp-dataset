__author__ = "Group 5"
import datetime
import psycopg2 as p
from pymongo import MongoClient
con = p.connect("dbname = 'ibd_group_project' user = 'harshraj' host = 'localhost'")
cur = con.cursor()
mclient = MongoClient('mongodb://localhost:27017')
print(mclient)
db = mclient['group_project_sql_to_mongo']
print(db)
business = db['business']
users = db['users']
review = db['review']
tip = db['tip']

def loadDataIntoBusiness():
    '''
    This method loads the Business Collection
    :return:
    '''
    cur.execute("SELECT array_agg(category.category_type) AS category, business.*, "
                "address.city,address.state,address.postal_code,rating.star,rating.review_count FROM business "
                "LEFT JOIN business_category ON business_category.business_id = business.business_id "
                "LEFT JOIN category ON category.id = business_category.category_id "
                "LEFT JOIN address ON address.business_id = business.business_id "
                "LEFT JOIN rating ON rating.business_id = business.business_id "
                "GROUP BY business.business_id,address.city,address.state,address.postal_code,rating.star,rating.review_count ")
    result = cur.fetchall()
    businessList = []
    for r in result:
        businessDict = {}
        businessDict['_id'] = r[1]
        businessDict['name'] = r[2]
        businessDict['isOpen'] = r[3]
        businessDict['isTakeout'] = r[4]
        addressDict={}
        addressDict['city'] = r[5]
        addressDict['state'] = r[6]
        addressDict['postalCode'] = r[7]
        businessDict['address'] = addressDict
        businessDict['star'] = r[8]
        businessDict['reviewCount'] = r[9]
        if r[0][0] != None:
            businessDict['categories'] =r[0]
        businessList.append(businessDict)
    business.insert_many(businessList)

def loadDataIntoUsers():
    '''
    This method loads the Users Collection
    :return:
    '''
    cur.execute("SELECT * FROM users")
    result = cur.fetchall()
    usersList = []
    for r in result:
        userDict = {}
        userDict['_id'] = r[0]
        userDict['name'] = r[1]
        userDict['reviewCount'] = r[2]
        userDict['fans'] = r[3]
        userDict['avgStars'] = r[4]
        usersList.append(userDict)

    users.insert_many(usersList)

def loadDataIntoReviews():
    '''
    This method loads the Reviews Collection
    :return:
    '''
    cur.execute("SELECT * FROM review")
    result = cur.fetchall()
    reviewList = []
    for r in result:
        reviewDict = {}
        reviewDict['_id'] = r[0]
        reviewDict['userId'] = r[1]
        reviewDict['businessId'] = r[2]
        reviewDict['stars'] = r[3]
        dateStr = str(r[4])
        date = datetime.datetime.strptime(dateStr, '%Y-%m-%d')
        reviewDict['reviewDate'] = date
        reviewList.append(reviewDict)
    review.insert_many(reviewList)
    print("Done inserting Reviews")

def loadDataIntoTip():
    '''
    This method loads the Tip Collection
    :return:
    '''
    cur.execute("SELECT * FROM tip")
    result = cur.fetchall()
    tipList = []
    for r in result:
        tipDict = {}
        tipDict['userId'] = r[0]
        tipDict['businessId'] = r[1]
        tipDict['tip'] = r[2]
        tipDict['complementCount'] = r[3]
        dateStr = str(r[4])
        date = datetime.datetime.strptime(dateStr, '%Y-%m-%d')
        tipDict['tipDate'] = date
        tipList.append(tipDict)
    tip.insert_many(tipList)
    print("Done inserting Tip")
    
loadDataIntoBusiness()
loadDataIntoUsers()
loadDataIntoReviews()
loadDataIntoTip()