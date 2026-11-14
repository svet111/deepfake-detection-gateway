# Deepfake Detection Gateway

A full-stack web application that detects whether an uploaded image is real or an AI-generated deepfake.

## Architecture

This project uses two microservices running together via Docker Compose:
- **Gateway Service (Java 21 / Spring Boot):** Serves the web interface, handles image uploads, and saves scan results to an H2 database.
- **AI Detector Service (Python 3.11 / FastAPI):** Runs a Hugging Face Vision Transformer model (`dima806/deepfake_vs_real_image_detection`) to classify images and return a confidence score.

## Prerequisites

- Docker Desktop installed and running

## How to Run

1. Clone the repository and open the project folder in your terminal.
2. Build and start both containers:
   ```bash
   docker compose up --build
   ```
3. Open your browser and go to http://localhost:8080.
4. Upload an image and click Scan File to see the prediction.
## API Endpoint
POST /api/v1/scans — Accepts a multipart/form-data image file and returns the classification result and confidence score as JSON.