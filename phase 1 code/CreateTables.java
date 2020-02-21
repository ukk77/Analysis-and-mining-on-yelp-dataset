import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateTables {
    static Connection c;
    public static void main(String[] args) throws Exception{

        Properties prop = new Properties();
        InputStream in = CreateTables.class.getResourceAsStream("db.properties");
        prop.load(in);
        System.out.println(prop.getProperty("url"));
        System.out.println(prop.getProperty("username"));
        System.out.println(prop.getProperty("password"));
        in.close();
        try {
            c = DriverManager
                    .getConnection(prop.getProperty("url"),
                            prop.getProperty("username"), prop.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //creates the table Business
        createBusinessTable();
        //creates the table Address
        createAddressTable();
        //creates the table Rating
        createRatingTable();
        //creates the table users
        createUsersTable();
        //creates the table tip
        createTipTable();
        //creates the table Review
        createReviewTable();
        //creates the table category
        createCategoryTable();
        //creates the table BusinessCategory
        createBusinessCategoryTable();
        c.close();
    }

    /**
     * creates Business_Category Table
     */
    private static void createBusinessCategoryTable() {
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE BUSINESS_CATEGORY " +
                    "(BUSINESS_ID VARCHAR(200),"+
                    " CATEGORY_ID INT," +
                    "constraint fk_business_id" +
                    "       foreign key (BUSINESS_ID) " +
                    "       REFERENCES BUSINESS (BUSINESS_ID)," +
                    "constraint fk_category_id" +
                    "       foreign key (CATEGORY_ID) " +
                    "       REFERENCES CATEGORY (ID)" +
                    " );";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("business_category Table created successfully");
    }

    /**
     * creates Category Table
     */
    private static void createCategoryTable() {
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE CATEGORY " +
                    "(ID  INT PRIMARY KEY," +
                    " CATEGORY_TYPE   TEXT );";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Category Table created successfully");
    }

    /**
     * creates Review Table
     */
    private static void createReviewTable() {
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE REVIEW " +
                    "(REVIEW_ID VARCHAR(200),"+
                    " USER_ID VARCHAR(200) ," +
                    " BUSINESS_ID VARCHAR(200)," +
                    " STARS FLOAT (2) ," +
                    " REVIEW_DATE DATE,"+
                    "constraint fk_business_id" +
                    "       foreign key (BUSINESS_ID) " +
                    "       REFERENCES BUSINESS (BUSINESS_ID)," +
                    "constraint fk_user_id" +
                    "       foreign key (USER_ID) " +
                    "       REFERENCES USERS (USER_ID)" +
                    " );";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("review Table created successfully");
    }

    /**
     * creates Tip table
     */
    private static void createTipTable() {
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE TIP " +
                    "(USER_ID VARCHAR(200) ," +
                    " BUSINESS_ID VARCHAR(200)," +
                    " TIP TEXT ," +
                    " COMPLEMENT_COUNT INT,"+
                    " TIP_DATE DATE,"+
                    "constraint fk_business_id" +
                    "       foreign key (BUSINESS_ID) " +
                    "       REFERENCES BUSINESS (BUSINESS_ID)," +
                    "constraint fk_user_id" +
                    "       foreign key (USER_ID) " +
                    "       REFERENCES USERS (USER_ID)" +
                    " );";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Tip Table created successfully");
    }

    /**
     * creates User table
     */
    private static void createUsersTable() {
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE USERS " +
                    "(USER_ID  VARCHAR(200) PRIMARY KEY," +
                    " NAME TEXT," +
                    " REVIEW_COUNT INT,"+
                    " FANS INT,"+
                    "AVERAGE_STARS FLOAT (2) "+
                    " );";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("users Table created successfully");
    }

    /**
     * creates Rating table
     */
    private static void createRatingTable() {

        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE RATING " +
                    "(BUSINESS_ID  VARCHAR(200) PRIMARY KEY," +
                    " STAR FLOAT (2) ," +
                    " REVIEW_COUNT INT,"+
                    "constraint fk_business_id" +
                    "       foreign key (BUSINESS_ID) " +
                    "       REFERENCES BUSINESS (BUSINESS_ID)" +
                    " );";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Rating Table created successfully");

    }

    /**
     * Creates Address Table
     */
    private static void createAddressTable() {

        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE ADDRESS " +
                    "(BUSINESS_ID  VARCHAR(200) PRIMARY KEY," +
                    " CITY TEXT ," +
                    " STATE TEXT ," +
                    " POSTAL_CODE TEXT ," +
                    "constraint fk_business_id" +
                    "       foreign key (BUSINESS_ID) " +
                    "       REFERENCES BUSINESS (BUSINESS_ID)" +
                    " );";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("ADDRESS Table created successfully");

    }

    /**
     * Creates Business Table
     */
    private static void createBusinessTable() {

        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE BUSINESS " +
                    "(BUSINESS_ID  VARCHAR(200) PRIMARY KEY," +
                    " NAME   TEXT ," +
                    " IS_OPEN   BOOLEAN ," +
                    " IS_TAKEOUT BOOLEAN);";


            stmt.executeUpdate(sql);
            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Business Table created successfully");

    }

}
