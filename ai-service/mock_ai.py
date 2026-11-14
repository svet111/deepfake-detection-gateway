from fastapi import FastAPI, File, UploadFile

app = FastAPI()

@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    is_fake = "fake" in file.filename.lower()
    return {
        "isDeepfake": is_fake,
        "confidenceScore": 0.94 if is_fake else 0.12
    }
