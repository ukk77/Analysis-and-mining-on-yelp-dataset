package model;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class InsertInDb {


    static Properties prop = null;
    static Connection c = null;


    public InsertInDb() throws Exception{
        prop = new Properties();
        InputStream in = InsertInDb.class.getResourceAsStream("/db.properties");
        prop.load(in);
        in.close();
        try {
            c = DriverManager
                    .getConnection(prop.getProperty("url"),
                            prop.getProperty("username"), prop.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts into Business Table
     * @param businessArrayList
     */
    public void insertInBusinessTable(ArrayList<Business> businessArrayList) {

        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO BUSINESS VALUES(?,?,?,?)");

            for (Business b : businessArrayList) {
                stmt.setString(1, b.getId());
                stmt.setString(2, b.getName());
                stmt.setBoolean(3, b.isOpen());
                stmt.setBoolean(4,b.isTakeOut());
                stmt.executeUpdate();
            }

            stmt.close();
            c.commit();
            //c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Business Records created successfully");

    }

    /**
     * Insert into Address Table
     * @param addressArrayList
     */
    public void insertInAddress(ArrayList<Address> addressArrayList) {
        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO ADDRESS VALUES(?,?,?,?)");

            for (Address a : addressArrayList) {
                stmt.setString(1, a.getBusinessId());
                stmt.setString(2, a.getCity());
                stmt.setString(3, a.getState());
                stmt.setString(4,a.getPostalCode());
                stmt.executeUpdate();
            }

            stmt.close();
            c.commit();
            //c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Address Records created successfully");
    }

    /**
     * insert into Rating Table
     * @param ratingArrayList
     */
    public void insertInRatingTable(ArrayList<Rating> ratingArrayList) {

        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO RATING VALUES(?,?,?)");

            for (Rating r : ratingArrayList) {
                stmt.setString(1, r.getBusinessId());
                stmt.setFloat(2, r.getStar());
                stmt.setInt(3, r.getReviewCount());
                stmt.executeUpdate();
            }

            stmt.close();
            c.commit();
            //c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("rating Records created successfully");

    }

    /**
     * insert into Users table
     * @param userArrayList
     */
    public void insertInUsersTable(ArrayList<User> userArrayList) {
        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO USERS VALUES(?,?,?,?,?)");

            for (User u : userArrayList) {
                stmt.setString(1, u.getId());
                stmt.setString(2, u.getName());
                stmt.setInt(3, u.getReviewCount());
                stmt.setInt(4,u.getFans());
                stmt.setFloat(5,u.getAvgStars());
                stmt.executeUpdate();
            }

            stmt.close();
            c.commit();
            //c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("user Records created successfully");
    }

    /**
     * insert into Tip File
     * @param tipArrayList
     */
    public void insertIntoTipFile(ArrayList<Tip> tipArrayList) {
        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO TIP VALUES(?,?,?,?,?)");

            for (Tip t : tipArrayList) {
                stmt.setString(1, t.getUserId());
                stmt.setString(2, t.getBusinessId());
                stmt.setString(3, t.getText());
                stmt.setInt(4,t.getComplementCount());
                stmt.setDate(5, new java.sql.Date(t.getDate().getTime()));
                stmt.executeUpdate();
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("tip Records created successfully");
    }

    /**
     * insert into Review table
     * @param reviewArrayList
     */
    public void insertIntoReviewTable(ArrayList<Review> reviewArrayList) {

        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO REVIEW VALUES(?,?,?,?,?)");

            for (Review r : reviewArrayList) {
                stmt.setString(1, r.getReviewId());
                stmt.setString(2, r.getUserId());
                stmt.setString(3, r.getBusinessId());
                stmt.setFloat(4,r.getStars());
                stmt.setDate(5, new java.sql.Date(r.getDate().getTime()));
                stmt.executeUpdate();
            }

            stmt.close();
            c.commit();
            //c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("review Records created successfully");

    }

    /**
     * insert into category table
     * @param categoryHM
     */
    public void insertIntoCategoryTable(HashMap<String,Integer> categoryHM) {
        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO CATEGORY VALUES(?,?)");

            for (String category : categoryHM.keySet()) {
                stmt.setInt(1, categoryHM.get(category));
                stmt.setString(2, category);

                stmt.executeUpdate();
            }

            stmt.close();
            c.commit();
            //c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("category Records created successfully");
    }

    /**
     * insert into business category table
     * @param businessCategories
     */
    public void insertInBusinessCategoryTable(ArrayList<BusinessCategory> businessCategories) {
        PreparedStatement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("INSERT INTO BUSINESS_CATEGORY VALUES(?,?)");
            for (BusinessCategory bc : businessCategories) {
                stmt.setString(1, bc.getBusinessId());
                stmt.setInt(2, bc.getCategoryId());
                stmt.executeUpdate();

            }
            stmt.close();
            c.commit();
            //c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("business category Records created successfully");
    }
}
