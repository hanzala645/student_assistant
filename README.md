AI Student Assistant
Project Report
Status: Fully Functional, Ready for Deployment
Date: October 26, 2023
Department of Computer Science
Android Application Development
Contents
1 ExecutiveSummary 2
2 CoreFeaturesImplemented 3
2.1 UserAuthentication . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 3
2.2 AnimatedHomeDashboard . . . . . . . . . . . . . . . . . . . . . . . . . 3
2.3 GrammarChecker . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 3
2.4 TextSummarizer . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 3
2.5 ScanText(OCR)Feature . . . . . . . . . . . . . . . . . . . . . . . . . . 4
2.6 SavedHistory . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 4
2.7 UserProfile . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 4
2.8 AnimatedSplashScreen . . . . . . . . . . . . . . . . . . . . . . . . . . . 4
3 TechnicalArchitectureandTechnologyStack 5
3.1 DevelopmentStack . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 5
3.2 BackendandCloudServices . . . . . . . . . . . . . . . . . . . . . . . . . 5
3.3 AIandMachineLearning . . . . . . . . . . . . . . . . . . . . . . . . . . 5
3.4 CameraandNetworking . . . . . . . . . . . . . . . . . . . . . . . . . . . 5
4 UIandUXDesignPhilosophy 6
5 RecommendationsandFutureEnhancements 7
6 Conclusion 8
1
1. Executive Summary
The AI Student Assistant is a comprehensive, feature-rich Android application de
signed to serve as an intelligent academic companion for students. Developed using Java
and a modern XML-based user interface, the application integrates multiple AI-powered
features to simplify everyday academic tasks. The system emphasizes a professional,
responsive, and visually appealing design while leveraging Google cloud services to en
sure scalability and reliability. All core objectives of the project have been successfully
achieved, and the application is fully functional and ready for deployment.
2
2. Core Features Implemented
The application consists of multiple fully functional modules that together provide a
complete and seamless user experience.
2.1 User Authentication
• Technology: Firebase Authentication
• Functionality: Secure user registration and login using email and password. Sep
arate and professionally designed screens are implemented for registration and login
processes.
2.2 Animated Home Dashboard
• Acts as the central navigation hub of the application.
• Features an animated banner displaying the app logo.
• Provides a grid-based layout with card-style buttons for navigating to major fea
tures.
2.3 Grammar Checker
• Technology: LanguageTool API, OkHttp, Google ML Kit, CameraX
• Allows users to input text manually or scan text using the camera.
• Provides accurate grammar corrections and suggestions via API integration.
2.4 Text Summarizer
• Technology: Local Extractive Algorithm, Google ML Kit, CameraX
• Summarizes long text passages efficiently on-device.
• Supports both typed input and scanned text from images.
3
2.5 Scan Text (OCR) Feature
• Technology: Google ML Kit Text Recognition and CameraX
• Provides a full-screen live camera preview.
• Extracted text is automatically transferred to grammar checking or summarization
modules.
2.6 Saved History
• Technology: Firebase Firestore, RecyclerView
• Stores grammar checks and summaries securely in the cloud.
• Displays saved records with timestamps in a clean card-based layout.
2.7 User Profile
• Displays user account information such as email address.
• Allows secure logout from the application.
2.8 Animated Splash Screen
• Features animated logo and application name.
• Provides a professional and visually engaging introduction to the app.
4
3. Technical Architecture and Technology Stack
The application is developed using a modern and scalable architecture to ensure perfor
mance and maintainability.
3.1 Development Stack
• Programming Language: Java
• UI Framework: Android XML with Material Design 3
• Architecture: Multi-Activity Architecture
3.2 Backend and Cloud Services
• Firebase Authentication for user management
• Firebase Firestore for cloud-based data storage
3.3 AI and Machine Learning
• Google ML Kit for text recognition (OCR)
• LanguageTool API for grammar checking
• Local extractive algorithm for text summarization
3.4 Camera and Networking
• CameraX for camera integration
• OkHttp for API communication
5
4. UI and UX Design Philosophy
A strong emphasis was placed on creating a professional and user-friendly interface.
• Centralized design system using colors.xml, styles.xml, themes.xml, and dimens.xml
• Modern card-based layouts with high-contrast color schemes
• Smooth animations such as fade-in and slide-down effects
• Consistent branding using a custom-designed logo
• Responsive layouts ensuring compatibility across multiple screen sizes
6
5. Recommendations and Future Enhancements
Although the project is complete, the following improvements are recommended:
1. Integration of cloud-based abstractive summarization using Google Gemini API
2. Addition of a settings module for user customization
3. Dedicated tablet UI layouts using sw600dp resources
4. Offline caching of user history for enhanced accessibility
7
6. Conclusion
The AI Student Assistant project has successfully achieved its objectives. The appli
cation is stable, feature-complete, and professionally designed, integrating AI capabilities
with cloud services to deliver a powerful academic support tool. With its scalable archi
tecture and polished user experience, the application is fully prepared for deployment on
the Google Play Store.
8
