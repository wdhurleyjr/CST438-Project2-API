## Database Setup

This project uses MongoDB as the primary database. It stores and manages data for various entities such as users, books, and user actions like adding items to wishlists. MongoDB provides a scalable, document-based solution ideal for our application\'s requirements. You can set it up locally or use MongoDB Atlas for a cloud-hosted solution.

## Prerequisites

- Install MongoDB on your local machine or create a MongoDB Atlas account.
- Ensure you have MongoDB Compass (optional) for a GUI-based interface to view your collections.
- Ensure your `application.properties` or `application.yml` file is configured correctly to point to the MongoDB instance.

### Local Setup

1. Install MongoDB: Follow the [MongoDB installation guide](https://docs.mongodb.com/manual/installation/) for your operating system.
2. Start MongoDB locally:
   \`\`\`
   mongod
   \`\`\`
3. Connect to your local MongoDB instance via MongoDB Compass or the CLI using:
   \`\`\`
   mongo
   \`\`\`
4. Create the database that will store your application\'s data:
   \`\`\`
   use <your-database-name>
   \`\`\`
5. Update your \`application.properties\` file in the \`src/main/resources\` directory to point to your local MongoDB instance:
   \`\`\`
   spring.data.mongodb.uri=mongodb://localhost:27017/<your-database-name>
   \`\`\`

### Cloud Setup (MongoDB Atlas)

1. Create an account on [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
2. Create a new cluster and database.
3. Whitelist your IP address to allow connections from your machine.
4. Obtain your MongoDB connection string and update the \`application.properties\` or \`application.yml\` file:
   \`\`\`
   spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/<your-database-name>?retryWrites=true&w=majority
   \`\`\`

### Database Usage in the Application

MongoDB is used throughout this project to manage data for the following cases:

1. **User Data:**
   - MongoDB stores user credentials, profile information, and roles.
   - Users can register, update their profiles, and manage their wishlist.
   - Each user\'s document in MongoDB contains fields such as username, email, hashed password, first name, last name, wishlist (list of book IDs), and roles.

2. **Book Data:**
   - Book information such as title, author, ISBN, category, image URLs, and descriptions are stored in MongoDB.
   - The database holds all available books in the collection, and users can search for books based on criteria like title, author, or ISBN.
   - Book objects are populated by the `/api/books/populate` endpoint, which seeds the database with sample data.



3. **Role-based Access Control:**
   - MongoDB stores user roles (e.g., `ROLE_USER`, `ROLE_ADMIN`). These roles determine the permissions each user has within the application.
   - When a user registers, they are assigned a default role (`ROLE_USER`).
   - Admins can assign additional roles to users, which are stored in the `roles` field of the user document.

### Database Initialization

To populate the database with initial data, including a set of predefined books, run the following command:
\`\`\`
curl -X GET http://localhost:8080/api/books/populate
\`\`\`

This will populate the `books` collection in MongoDB with sample data. You can verify the data has been inserted by checking the MongoDB collection using MongoDB Compass or by querying the database:
\`\`\`
db.books.find()
\`\`\`

### Further Operations

You can use MongoDB to handle more advanced operations, such as:
- **Querying the Database:** The application supports searching for books using the `/api/books/search` endpoint, which queries MongoDB based on title, author, or ISBN.
- **Database Updates:** Books and user details are updated automatically based on user interactions, such as updating their wishlist or profile.
- **Seeding Additional Data:** If needed, additional seeding scripts can be written to populate more data, and MongoDB allows importing large datasets via its CLI or MongoDB Compass.

Ensure that your MongoDB instance is up and running for these operations to function correctly.

' > docs/DatabaseSetup.md
