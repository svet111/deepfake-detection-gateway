import io
from fastapi import FastAPI, File, UploadFile
from PIL import Image
from transformers import pipeline

app = FastAPI()

classifier = pipeline("image-classification", model="dima806/deepfake_vs_real_image_detection")

@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    image_bytes = await file.read()
    image = Image.open(io.BytesIO(image_bytes)).convert("RGB")
    
    results = classifier(image)
    top_result = results[0]
    is_fake = top_result["label"].lower() == "fake"
    
    return {
        "isDeepfake": is_fake,
        "confidenceScore": round(top_result["score"], 4)
    }