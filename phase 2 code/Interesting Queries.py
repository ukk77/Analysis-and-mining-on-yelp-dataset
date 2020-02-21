import psycopg2 as psy
import time


def main():
    dbname = input("Enter the name of the database")
    usern = input("Enter the username")
    passwd = input("Enter the password")
    connection = psy.connect(host="localhost", database=dbname, user=usern, password=passwd)
    print("Connection has established")
    cur = connection.cursor()
    """
    FIRST Query
    Famous Restaurants by state that have rating greater than 4 and with a review count of at least > 100.
    """

    """
        Before Indexing:
    """
    start = time.time()
    cur.execute("SELECT name as Restaurants,a.city,a.state,star,review_count from business as b "
                "JOIN business_category as bc ON b.business_id = bc.business_id "
                "JOIN address as a ON a.business_id = b.business_id JOIN rating as r ON r.business_id = b.business_id "
                "WHERE category_id = '792' AND star >= '4' AND review_count >=100 ORDER BY state;")
    end = time.time()
    print("Time Taken Before Indexing is ", (end-start))

    cur.execute("CREATE INDEX ON rating(star);")
    cur.execute("CREATE INDEX ON rating(review_count);")
    """
        After Indexing:
    """
    start = time.time()
    cur.execute("SELECT name as Restaurants,a.city,a.state,star,review_count from business as b "
                "JOIN business_category as bc ON b.business_id = bc.business_id "
                "JOIN address as a ON a.business_id = b.business_id JOIN rating as r ON r.business_id = b.business_id "
                "WHERE category_id = '792' AND star >= '4' AND review_count >=100 ORDER BY state;")
    end = time.time()
    print("Time Taken After Indexing is ", (end - start))

    """
    Second Query
     5 star Pizza places in Las Vegas
    """
    """
    Before Indexing:
    """
    start = time.time()
    cur.execute("SELECT name FROM business as b JOIN address as add ON b.business_id = add.business_id "
                "JOIN business_category as bc ON bc.business_id = b.business_id "
                "JOIN rating as r ON r.business_id =  b.business_id JOIN category as c ON c.id = bc.category_id "
                "WHERE category_type = 'Pizza' and city ='Las Vegas' and star = 5;")
    end = time.time()
    print("Time Taken Before Indexing is ", (end - start))

    """
    After Indexing:
    """
    cur.execute("CREATE UNIQUE INDEX ON category(category_type);")
    cur.execute("CREATE INDEX ON address(city);")
    start = time.time()
    cur.execute("SELECT name FROM business as b JOIN address as add ON b.business_id = add.business_id "
                "JOIN business_category as bc ON bc.business_id = b.business_id "
                "JOIN rating as r ON r.business_id =  b.business_id JOIN category as c ON c.id = bc.category_id "
                "WHERE category_type = 'Pizza' and city ='Las Vegas' and star = 5;")
    end = time.time()
    print("Time Taken After Indexing is ", (end - start))

    """
    Third Query:
    All indian restaurants in toronto which have rating greater than 3
    """
    """
    Before Indexing:    
    """
    start = time.time()
    cur.execute("SELECT business.name, rating.star FROM business "
                "JOIN business_category ON business_category.business_id = business.business_id "
                "JOIN category ON category.id = business_category.category_id "
                "JOIN address ON address.business_id = business.business_id "
                "JOIN rating ON rating.business_id = business.business_id "
                "WHERE category.category_type LIKE 'Indian' AND address.city LIKE 'Toronto' AND rating.star > 3.0 "
                "ORDER BY rating.star DESC;")
    end = time.time()
    print("Time Taken Before Indexing is ", (end - start))
    """
    After Indexing:
    """
    cur.execute("CREATE UNIQUE INDEX category_category_type_index ON category (category_type);")
    cur.execute("CREATE  INDEX address_city_index ON address (city);")
    cur.execute("CREATE INDEX rating_star_index ON rating (star);")
    start = time.time()
    cur.execute("SELECT business.name, rating.star FROM business "
                "JOIN business_category ON business_category.business_id = business.business_id "
                "JOIN category ON category.id = business_category.category_id "
                "JOIN address ON address.business_id = business.business_id "
                "JOIN rating ON rating.business_id = business.business_id "
                "WHERE category.category_type LIKE 'Indian' AND address.city LIKE 'Toronto' AND rating.star > 3.0 "
                "ORDER BY rating.star DESC;")
    end = time.time()
    print("Time Taken After Indexing is ", (end - start))

    """
    Fourth Query:
    Best Beer Spots in Each City
    """
    start = time.time()
    cur.execute("select address.city,array_agg(business.name) from business "
                "JOIN business_category on business.business_id  = business_category.business_id "
                "JOIN category on category.id = business_category.category_id "
                "JOIN address on address.business_id = business.business_id "
                "JOIN rating on rating.business_id = business.business_id where category.category_type = 'Beer' "
                "and rating.star >= 4 group by address.city;")
    end = time.time()
    print("Time Taken Before Indexing is ", (end - start))

    """
    After Indexing:
    Indexes are commented because they are already created for the third query so they need not be repeated again.
    """
    #cur.execute("CREATE UNIQUE INDEX category_category_type_index ON category (category_type);")
    #cur.execute("CREATE  INDEX address_city_index ON address (city);")
    #cur.execute("CREATE INDEX rating_star_index ON rating (star);")
    start = time.time()
    cur.execute("select address.city,array_agg(business.name) from business "
                "JOIN business_category on business.business_id  = business_category.business_id "
                "JOIN category on category.id = business_category.category_id "
                "JOIN address on address.business_id = business.business_id "
                "JOIN rating on rating.business_id = business.business_id where category.category_type = 'Beer' "
                "and rating.star >= 4 group by address.city;")
    end = time.time()
    print("Time Taken After Indexing is ", (end - start))

    """
    Fifth Query: 
    States with the best police department.
    """
    """
    Before Indexing:
    """
    start = time.time()
    cur.execute("select address.state from business "
                "JOIN business_category on business.business_id = business_category.business_id "
                "JOIN category on category.id = business_category.category_id "
                "JOIN address on address.business_id = business.business_id "
                "JOIN rating on rating.business_id = business.business_id "
                "where category.category_type = 'Police Departments' and rating.star >= 3.5 group by address.state;")
    end = time.time()
    print("Time Taken Before Indexing is ", (end - start))
    """
    After Indexing:
    Indexes are commented because they are already created for the third query so they need not be repeated again.
    """
    #cur.execute("CREATE UNIQUE INDEX category_category_type_index ON category (category_type);")
    #cur.execute("CREATE  INDEX address_city_index ON address (city);")
    #cur.execute("CREATE INDEX rating_star_index ON rating (star);")
    start = time.time()
    cur.execute("select address.state from business "
                "JOIN business_category on business.business_id = business_category.business_id "
                "JOIN category on category.id = business_category.category_id "
                "JOIN address on address.business_id = business.business_id "
                "JOIN rating on rating.business_id = business.business_id "
                "where category.category_type = 'Police Departments' and rating.star >= 3.5 group by address.state;")
    end = time.time()
    print("Time Taken After Indexing is ", (end - start))
    connection.commit()
    cur.close()
    connection.close()
    print("Connection closed")


if __name__ == '__main__':
    main()
