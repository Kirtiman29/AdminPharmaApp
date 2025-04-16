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
![WhatsApp Image 2025-03-05 at 19 33 46_3380944b](https://github.com/user-attachments/assets/bc2fed19-7d32-4c0f-9528-d61883394cf2)
![WhatsApp Image 2025-03-05 at 19 33 47_8356985b](https://github.com/user-attachments/assets/d3e6b347-cdbb-46a5-8e6e-8ede7128f52e)
![WhatsApp Image 2025-03-05 at 19 33 47_a2c22942](https://github.com/user-attachments/assets/b8a0bd9d-fd79-4b53-b1d9-236f55b08318)
![WhatsApp Image 2025-03-05 at 19 33 47_6fedc047](https://github.com/user-attachments/assets/26cd0bb2-61d5-4787-bd40-741a0d50910e)
![WhatsApp Image 2025-03-05 at 19 33 46_9c243d64](https://github.com/user-attachments/assets/8ad7ac90-cb15-4f51-8fc0-582f10bd2a19)
![WhatsApp Image 2025-03-05 at 19 33 46_006d0b16](https://github.com/user-attachments/assets/93f8c473-711c-4bc3-bcca-53cb8b5cd01c)
![WhatsApp Image 2025-03-05 at 19 33 45_06254f69](https://github.com/user-attachments/assets/bdeeb7bd-bb68-47aa-b2a4-4055e63545cb)



_Add screenshots here of the Admin dashboard, product list, and user approval pages._

## 📦 Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/medical-b2b-admin-app.git
Open the project in Android Studio.

Ensure the Flask API is live at [https://yourusername.pythonanywhere.com/](https://workkirtiman.pythonanywhere.com/).

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

