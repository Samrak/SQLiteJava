# README #

### What is this repository for? ###

* Easy SQLite using in Android with Java Programming.
* 1.0

### How do I get set up? ###

* build.gradle

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
		compile 'com.github.Samrak:SQLiteJava:-SNAPSHOT'
	}
  
### How do I create object and database table mapping? ###
 
 EntityMaterial.java or Material.java //doesn't matter.

    public class EntityMaterial {
        private long materialId;
        private String materialName;
        private String materialSurname;
        private String materialData;

    public EntityMaterial() {
    }

    public EntityMaterial(long materialId, String materialName, String materialSurname, String materialData) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialSurname = materialSurname;
        this.materialData = materialData;
    }

    public long getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialSurname() {
        return materialSurname;
    }

    public String getMaterialData() {
        return materialData;
        }
    }

Database table column names must match with property names.

    public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper dataBaseHelper;
    static final String DATABASE = "activate";
    static final int DATABASE_VERSION = 1;

    static String createTable2 = "CREATE TABLE " + "material" + " ( " + "materialId" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "materialName" + " VARCHAR(10), "
            + "materialSurname" + " NVARCHAR(15), "
            + "materialData" + " TEXT  )";


    public SQLiteHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + createTable2);
        onCreate(db);
    }

    public static SQLiteHelper getInstance(Context context) {
        return dataBaseHelper = (dataBaseHelper == null) ? new SQLiteHelper(context) : dataBaseHelper;
        }
    }
    
### How do I use in Project? ###

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    SQLiteAdapter db = new SQLiteAdapter(dbHelper);

There is only 4 elements to manipulate SQLite Database.

    db.insert(new EntityMaterial(0, "samet", "öztoprak", "data"), "materialId")
    db.delete(new EntityMaterial(2, "samet", "öztoprak", "data"), "materialId")
    db.update(new EntityMaterial(2, "samet", "öztoprak", "data"), "materialId")

    ArrayList<EntityMaterial> materials = db.select(new EntityTest())
    ArrayList<EntityMaterial> materials = db.select(new EntityTest(),"123") // all column with like "123" query
    ArrayList<EntityMaterial> materials = db.select(new EntityTest(),"id","23") // column name and value

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Samet Öztoprak sametoztoprak@hotmail.com
