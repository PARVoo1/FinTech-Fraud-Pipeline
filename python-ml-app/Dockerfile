# 1. Start with a lightweight Python OS
FROM python:3.11-slim

# 2. Set the working directory
WORKDIR /app

# 3. Copy requirements and install
COPY requirements.txt .

# NEW: We added --default-timeout=1000 to prevent network crashes!
RUN pip install --default-timeout=1000 --no-cache-dir -r requirements.txt

# 4. Copy your actual code
COPY main.py .

# 5. Run the background worker script directly!
CMD ["python", "-u", "main.py"]