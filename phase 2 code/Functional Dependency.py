import psycopg2 as psy
from itertools import combinations

"""
    Below loop calculates the possible functional dependencies using naive approach. Each time a query is passed
    for a different LHS->RHS combination, if the query results in null, it means functional dependencies exists. These
    are appended to a temporary list which are returned to the main function.
"""


def naiveapproach(combos, tempcombos, tablename, cur):
    templist = []
    filler2 = ""
    for i in range(len(combos)):
        filler1 = ""
        for k in range(len(combos[i])):
            filler1 += combos[i][k]
            if k == len(combos[i])-1:
                pass
            else:
                filler1 += ","
        for j in range(len(tempcombos)):
            for m in range(len(tempcombos[j])):
                filler2 += " COUNT (DISTINCT "+tempcombos[j][m]+" )>1 "
                if m == len(tempcombos[j]) - 1:
                    pass
                else:
                    filler2 += "AND "
            cur.execute("SELECT "+str(filler1)+" FROM "+str(tablename)+" GROUP BY "+str(filler1)+" HAVING " +
                        str(filler2)+";")
            fetch = cur.fetchone()
            if fetch is None:
                temp = str(filler1) + "-->"+str(filler2)
                templist.append(temp)
            filler2 = ""
    for i in templist:
        print(i)


"""
        This method will give all the possible combinations for a given list.
"""


def listgenerator(combolist):
    combos = sum([list(map(list, combinations(combolist, i))) for i in range(len(combolist) + 1)], [])
    combos = combos[1:]
    print(" ")
    print(combos)
    print()
    print()
    newcombos = []
    for i in combos:
        if 'business_id' in i:
            pass
        else:
            newcombos.append(i)
    print(newcombos)
    return combos, newcombos


def main():
    dbname = input("Enter the name of the database\n")
    usern = input("Enter the username\n")
    passwd = input("Enter the password\n")
    dbname = "groupproject"
    usern = "postgres"
    passwd = "1234"
    connection = psy.connect(host="localhost", database=dbname, user=usern, password=passwd)
    print("Connection has established")
    cur = connection.cursor()

    tablename = "address"
    print(tablename)
    combolist = ['business_id', 'city', 'state', 'postal_code']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)

    tablename = "business"
    print(tablename)
    combolist = ['business_id', 'name', 'is_open', 'is_takeout']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)

    tablename = "business_category"
    print(tablename)
    combolist = ['business_id', 'category_id']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)

    tablename = "category"
    print(tablename)
    combolist = ['id', 'category_type']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)

    tablename = "rating"
    print(tablename)
    combolist = ['business_id', 'star', 'review_count']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)

    tablename = "tip"
    print(tablename)
    combolist = ['business_id', 'tip', 'user_id', 'complement_count', 'tip_date']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)

    tablename = "users"
    print(tablename)
    combolist = ['user_id', 'name', 'review_count', 'fans', 'average_stars']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)

    tablename = "review"
    print(tablename)
    combolist = ['review_id', 'user_id', 'business_id', 'stars', 'review_date']
    combos, newcombos = listgenerator(combolist)
    naiveapproach(newcombos, combos, tablename, cur)
    connection.commit()
    cur.close()
    connection.close()
    print("Connection closed")


if __name__ == '__main__':
    main()

