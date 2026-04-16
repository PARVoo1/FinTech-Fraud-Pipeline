import json
import sys
import time
import logging
import pandas as pd
from sklearn.ensemble import IsolationForest
from kafka import KafkaConsumer, KafkaProducer
from kafka.errors import NoBrokersAvailable

# 1. Professional Logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s | %(levelname)-8s | %(message)s', stream=sys.stdout)
logger = logging.getLogger(__name__)

# 2. Train the ML Model
logger.info("📊 Training Isolation Forest on historical data...")
historical_data = pd.DataFrame({'amount': [10, 50, 100, 20, 500, 30, 200, 150, 80, 45, 80000, 95000]})
ml_model = IsolationForest(contamination=0.1, random_state=42)
ml_model.fit(historical_data[['amount']])
logger.info("✅ ML Model successfully trained!")

# 3. Connect to Kafka (Both Listening AND Speaking)
consumer = None
producer = None
while consumer is None or producer is None:
    try:
        # The Ears
        consumer = KafkaConsumer('fraud-detection-queue', bootstrap_servers=['kafka:9092'], value_deserializer=lambda m: json.loads(m.decode('utf-8')))
        # The Mouth
        producer = KafkaProducer(bootstrap_servers=['kafka:9092'], value_serializer=lambda v: json.dumps(v).encode('utf-8'))
        logger.info("✅ Connected to the Kafka Highway (Two-Way Communication Enabled)!")
    except NoBrokersAvailable:
        logger.warning("⏳ Kafka isn't ready yet. Retrying in 5 seconds...")
        time.sleep(5)

# 4. The Live Prediction Loop
logger.info("🎧 Listening for live transactions...")

for message in consumer:
    transaction = message.value

    # We grab the PostgreSQL ID that Java generated!
    transaction_id = transaction.get('id', 'UNKNOWN')
    account_id = transaction.get('accountId', 'UNKNOWN')
    amount = transaction.get('amount', 0.0)

    # Make the Prediction
    prediction = ml_model.predict(pd.DataFrame({'amount': [amount]}))[0]

    if prediction == -1:
        logger.error(f"🚨 ML FRAUD ALERT: High risk anomaly! ₹{amount} by Account {account_id}!")
        final_status = "REJECTED"
    else:
        logger.info(f"✅ SAFE: ML verified transaction of ₹{amount} for Account {account_id}.")
        final_status = "APPROVED"

    # 5. THE RETURN TRIP: Send the decision back to Java
    result_message = {
        "transactionId": transaction_id,
        "status": final_status
    }
    producer.send('fraud-results-queue', result_message)
    producer.flush() # Force it to send immediately
    logger.info(f"✉️ Decision ({final_status}) sent back to Java for Transaction {transaction_id}")