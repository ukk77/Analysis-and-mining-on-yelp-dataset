import com.google.gson.*;
import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoadData {
    private static Properties prop;
    private static  ArrayList<Business> businessArrayList;
    private static ArrayList<Address> addressArrayList;
    private static ArrayList<Rating> ratingArrayList;
    private static HashSet<String> categoryHS;
    private static HashMap<String,Integer> categoryHM;
    private static ArrayList<User> userArrayList;
    private static ArrayList<Review> reviewArrayList;
    private static ArrayList<Tip> tipArrayList;
    private static ArrayList<BusinessCategory>  businessCategories;

    public static void main(String[] args) throws Exception{

        prop = new Properties();
        InputStream in = LoadData.class.getResourceAsStream("file.properties");
        prop.load(in);
        in.close();
        InsertInDb insert = new InsertInDb();
        //loads data from business.json file
        loadBusinessFile();
        //Inserts into Business Table
        insert.insertInBusinessTable(businessArrayList);
        //Insert into Address Table
        insert.insertInAddress(addressArrayList);
        addressArrayList = null;
        //Insert into Rating Table
        insert.insertInRatingTable(ratingArrayList);
        ratingArrayList = null;
        //Creates Categories HashMap
        createCategoriesHM();
        //Insert into Category Table
        insert.insertIntoCategoryTable(categoryHM);
        //Created arraylist of type businessCategory
        loadBusinessCategoryTableEntry();
        //Insert into BusinessCategory table
        insert.insertInBusinessCategoryTable(businessCategories);
        businessArrayList = null;
        categoryHS = null;
        categoryHM = null;
        // loads user.json file details into arraylist of type Users
        loadUsersFile();
        // Insert intp User table
        insert.insertInUsersTable(userArrayList);
        userArrayList = null;
        //loads Review.json
        loadReviewFile();
        //insert Into Review Table
        insert.insertIntoReviewTable(reviewArrayList);
        reviewArrayList = null;
        //loads tip.json file
        loadTipFile();
        //inserts into Tip table
        insert.insertIntoTipFile(tipArrayList);
        tipArrayList = null;
    }

    /**
     * processes data to be entered into BusinessCategory table
     * @throws Exception
     */
    private static void loadBusinessCategoryTableEntry() throws Exception{
        businessCategories = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("businessFile")))) {
            String line;
            while ((line = br.readLine()) != null) {
                JsonElement jelement = new JsonParser().parse(line);
                JsonObject jobject = jelement.getAsJsonObject();
                String businessId = jobject.get("business_id").getAsString();
                if(jobject.has("categories") ) {
                    JsonElement element = jobject.get("categories");
                    if (!(element instanceof JsonNull)) {
                        String str = element.getAsString();
                        List<String> elephantList = Arrays.asList(str.split(","));
                        for (String s : elephantList){
                            if (categoryHS.contains(s))
                                businessCategories.add(new BusinessCategory(businessId,categoryHM.get(s)));

                        }
                    }
                }

            }
        }

    }

    /**processes tip.json file
     *
     * @throws Exception
     */
    private static void loadTipFile() throws Exception{
        tipArrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("tipFile")))) {
            String line;
            while ((line = br.readLine()) != null) {

                JsonElement jelement = new JsonParser().parse(line);
                JsonObject jsonObject = jelement.getAsJsonObject();
                String userId = jsonObject.get("user_id").getAsString();
                String businessId = jsonObject.get("business_id").getAsString();
                String text  = jsonObject.get("text").getAsString();
                int count = jsonObject.get("compliment_count").getAsInt();
                String dateStr = jsonObject.get("date").getAsString();
                DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
                Date date = format.parse(dateStr);

                tipArrayList.add(new Tip(userId,businessId,text,count,date));
            }
        }


    }

    /**processes review.json file
     *
     * @throws Exception
     */
    private static void loadReviewFile() throws Exception {
        reviewArrayList = new ArrayList<>();
        System.out.println("in loadReviewFile");
        try (BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("reviewFile")))) {
            String line;
            while ((line = br.readLine()) != null) {

                JsonElement jelement = new JsonParser().parse(line);
                JsonObject jsonObject = jelement.getAsJsonObject();
                String reviewId = jsonObject.get("review_id").getAsString();
                String userId = jsonObject.get("user_id").getAsString();
                String businessId = jsonObject.get("business_id").getAsString();
                float stars = jsonObject.get("stars").getAsFloat();
                String date1 = jsonObject.get("date").getAsString();
                DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
                Date date = format.parse(date1);
                reviewArrayList.add(new Review(reviewId, userId, businessId, stars, date));
            }
        }
    }


    /**processes user.json file
     *
     * @throws Exception
     */
    private static void loadUsersFile() throws Exception{
        userArrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("userFile")))) {
            String line;
            while ((line = br.readLine()) != null) {
                JsonElement jelement = new JsonParser().parse(line);
                JsonObject jsonObject = jelement.getAsJsonObject();
                String id = jsonObject.get("user_id").getAsString();
                String name = jsonObject.get("name").getAsString();
                int reviewCount = jsonObject.get("review_count").getAsInt();
                int fans = jsonObject.get("fans").getAsInt();
                float avgStars = jsonObject.get("average_stars").getAsFloat();

                userArrayList.add(new User(id,name,reviewCount,fans,avgStars));
            }
        }


    }

    /**creates category Hashmap
     *
     */
    private static void createCategoriesHM() {
        categoryHM = new HashMap<>();
        int i = 1;
        for (String s:categoryHS){
            categoryHM.put(s,i);
            i++;
        }
    }

    /**processes business.json
     *
     * @throws Exception
     */
    private static void loadBusinessFile()throws Exception {
        businessArrayList = new ArrayList<>();
        addressArrayList = new ArrayList<>();
        ratingArrayList = new ArrayList<>();
        categoryHS = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("businessFile")))) {
            String line;
            while ((line = br.readLine()) != null) {

                JsonElement jelement = new JsonParser().parse(line);
                JsonObject jobject = jelement.getAsJsonObject();
                String id = jobject.get("business_id").getAsString();;
                String businessName = jobject.get("name").getAsString();
                boolean isOpen = jobject.get("is_open").getAsInt() == 1 ? true:false;;
                boolean isRestaurantsTakeOut = false;
                if(jobject.has("attributes") ){
                    JsonElement element = jobject.get("attributes");
                    if (!(element instanceof JsonNull)) {
                        JsonObject attObj = (JsonObject) element;
                        if (attObj.has("RestaurantsTakeOut")){
                            isRestaurantsTakeOut = attObj.get("RestaurantsTakeOut").getAsBoolean();
                        }
                    }

                }
                String street = jobject.get("address").getAsString();
                String city = jobject.get("city").getAsString();
                String state = jobject.get("state").getAsString();
                String postalCode = jobject.get("postal_code").getAsString();
                float latitude = jobject.get("latitude").getAsFloat();
                float longitute = jobject.get("longitude").getAsFloat();

                float stars = jobject.get("stars").getAsFloat();
                int reviews = jobject.get("review_count").getAsInt();


                if(jobject.has("categories") ) {
                    JsonElement element = jobject.get("categories");
                    if (!(element instanceof JsonNull)) {
                       String str = element.getAsString();
                        //System.out.println(id+" - "+str);
                        List<String> elephantList = Arrays.asList(str.split(","));
                        for (String s : elephantList){
                            categoryHS.add(s.trim());
                        }
                    }
                }

                businessArrayList.add(new Business(id,businessName,isOpen,isRestaurantsTakeOut));
                addressArrayList.add(new Address(id,street,city,state,postalCode,latitude,longitute));
                ratingArrayList.add(new Rating(id,stars,reviews));

            }
        }


    }

}
