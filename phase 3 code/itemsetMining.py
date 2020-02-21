import psycopg2 as pg
import time


def main():
    db = input("enter dbname")
    user = input("enter user name")
    password = input("enter password")
    con = pg.connect(host="localhost", database=db, user=user, password=password)
    cur = con.cursor()
    buildbesttipstable(cur) #best_tips table from tips table
    tablecount = 1
    count = 1
    select = ""
    jselect = ""
    having = "having count(*) > 1"
    t = time.time()
    while tablecount > 0:
        flag = True
        if count == 1:
            state = "select user_id as user1,count(*) into L1 from best_tips group by user_id having count(*) > 1"
            select = ""
            jselect = ""
            groupby = ""
            having = "having count(*) > 1"
            cur.execute(state)
            cur.execute("select * from L1")
            tablecount = len(cur.fetchall())
            print(state)
            print("number of rows : "+str(tablecount))
            print("----------------------------------------------------------------------------------------")
            count += 1
        else:
            state = ""
            for i in range(count):
                select += "t"+str(i+1)+".user"+str(i+1)+","
            select+="count(*) "
            for i in range(count-1):
                if flag == True:
                    jselect += "(select Distinct user"+str(i+1)+",business_id from L"+str(count-1)+" JOIN " \
                            "best_tips on L"+str(count-1)+".user"+str(i+1)+"=best_tips.user_id) as t"+str(i+1)+" "
                    flag = False
                else:
                    jselect += "JOIN (select distinct user"+str(i+1)+",business_id from L"+str(count-1)+" JOIN " \
                               "best_tips on L"+str(count-1)+".user"+str(i+1)+"=best_tips.user_id) as t"+str(i+1)+" " \
                                "on t"+str(i-1+1)+".business_id = t"+str(i+1)+".business_id and t"+str(i-1+1)+".user"+str(i-1+1)+" < t"+str(i+1)+".user"+str(i+1)+" "
            jselect += " JOIN (select user_id as user"+str(count)+",business_id from best_tips) as t"+str(count)+" " \
                        "on t"+str(count-1)+".business_id = t"+str(count)+".business_id and t"+str(count-1)+".user"+str(count-1)+" < " \
                        "t"+str(count)+".user"+str(count)+" "
            groupby = "group by "
            for i in range(count):
                if i == count-1:
                    groupby += "t"+str(i+1)+".user"+str(i+1)+" "
                else:
                    groupby += "t" + str(i+1) + ".user" + str(i+1) + ","
            state = "select "+select +" into L"+str(count)+" from "+jselect + groupby + having
            cur.execute(state)
            print("query : ")
            print(state)
            cur.execute("select * from L"+str(count))
            tablecount = len(cur.fetchall())
            print("number of rows : " +str(tablecount))
            print("--------------------------------------------------------------------------------------")
            count += 1
            select = ""
            jselect = ""
            groupby = ""
            having = "having count(*) > 1"

    print("totale execution time "+str(time.time()-t))
    con.commit()
    getresults(cur)


"""
this function basically builds the final 
results from the L5 table (last non empty table)
"""

def getresults(cur):
    cur.execute("select t1.name,t2.name,t3.name,t4.name,t5.name,count "
                "from L5 JOIN users as t1 on t1.user_id = user1 JOIN "
                "users as t2 on t2.user_id = user2 JOIN "
                "users as t3 on t3.user_id = user3 JOIN "
                "users as t4 on t4.user_id = user4 JOIN "
                "users as t5 on t5.user_id = user5")
    data = cur.fetchall()
    for row in data:
       tempstr = 'users : '
       for i in range(len(row)-1):
           tempstr += row[i]+", "
       tempstr += "visited frequency :"+str(row[len(row)-1])
       print(tempstr)


"""
This function basically builds the best_tips table
"""

def buildbesttipstable(cur):
    cur.execute("select user_id,business_id into best_tips from tip where complement_count > 0")
    cur.execute("select * from best_tips")
    print("total rows for best_tips from tips "+str(len(cur.fetchall())))
    print("best_tips table built")
    print("------------------------------------------------------------------------------------")

if __name__ == '__main__':
    main()