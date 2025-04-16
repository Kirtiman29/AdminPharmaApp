# Medical B2B Admin App

This is the **Admin version** of the Medical B2B Android Application built using **Jetpack Compose**. The app connects to a **Flask REST API** hosted on **PythonAnywhere** for handling database operations and user management.

## 🚀 Features

- 🔐 Admin login and authentication
- 👥 View and approve user registration requests
- 📦 Add, update, and delete medical products
- ✅ Confirm user orders
- 📧 Send order confirmation emails to users
- 🧭 Clean, modern UI using Jetpack Compose

## 🛠️ Tech Stack

- **Frontend**: Jetpack Compose (Kotlin)
- **Backend**: Python Flask REST API (hosted on PythonAnywhere)
- **Database**: Accessed through API (MySQL on server)
- **Tools**: Android Studio, Postman, PythonAnywhere

## 📷 Screenshots

_Add screenshots here of the Admin dashboard, product list, and user approval pages._

## 📦 Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/medical-b2b-admin-app.git
Open the project in Android Studio.

Ensure the Flask API is live at https://yourusername.pythonanywhere.com/.

Update base API URLs in your networking layer (Retrofit or equivalent).

Build and run the app on your emulator or physical device.

📂 Folder Structure
├── ui/
│   ├── screens/
│   ├── components/
├── data/
│   ├── model/
│   ├── repository/
├── network/
│   └── api/
├── viewmodel/

