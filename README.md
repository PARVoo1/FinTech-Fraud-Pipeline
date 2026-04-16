# FinTech-Fraud-Pipeline 💳🔐

An end-to-end fraud detection system built for fintech transactions. Real-time fraud detection using machine learning, with a modern tech stack combining Spring Boot, Python ML models, and Kafka streaming.

## What This Does

This project catches fraudulent transactions in real-time. When a user creates a transaction, it gets sent through a fraud detection pipeline powered by an ML model. The system then either approves or rejects the transaction and notifies the user instantly through WebSocket updates.

Think of it as a security guard for your fintech platform that never sleeps.

## Tech Stack

**Backend:**
- Spring Boot 3.2.5 (Java 21)
- PostgreSQL (database)
- Redis (caching)
- WebSocket (real-time updates)

**ML & Streaming:**
- Python with scikit-learn (Isolation Forest model)
- Apache Kafka (message broker)
- Kafka KRaft mode (simplified setup)

**Frontend:**
- Plain HTML/CSS/JavaScript
- STOMP WebSocket client
- Real-time transaction dashboard

**Infrastructure:**
- Docker & Docker Compose (containerization)
- JWT authentication
- Google OAuth 2.0 integration

## Project Structure

```
├── FintechApp/                 # Spring Boot application
│   ├── src/
│   │   ├── main/java/          # Java source code
│   │   ├── resources/          # Configuration files
│   │   └── test/               # Unit tests
│   ├── pom.xml                 # Maven dependencies
│   └── Dockerfile              # Java container
│
├── python-ml-app/              # ML worker service
│   ├── main.py                 # Fraud detection logic
│   ├── requirements.txt         # Python dependencies
│   └── Dockerfile              # Python container
│
├── frontend/                   # Dashboard UI
│   └── dashboard.html          # Live transaction dashboard
│
└── docker-compose.yml          # Orchestration config
```

## How It Works

1. **User submits a transaction** through the Spring Boot REST API
2. **Transaction gets stored** in PostgreSQL and sent to Kafka
3. **Python worker listens** to Kafka queue and runs ML prediction
4. **Isolation Forest model** analyzes transaction amount for anomalies
5. **Decision goes back** through Kafka to the Java app
6. **User gets notified** in real-time via WebSocket
7. **Email alert sent** if transaction was flagged as suspicious

## Getting Started

### Prerequisites

- Docker & Docker Compose installed
- Maven 3.6+ (for local Java development)
- Python 3.11+ (for local ML development)
- Git

### Quick Start with Docker

```bash
# Clone the repo
git clone https://github.com/yourusername/FinTech-Fraud-Pipeline.git
cd FinTech-Fraud-Pipeline

# Build and start all services
docker-compose up --build
```

This will spin up:
- Kafka broker on `localhost:9092`
- Spring Boot API on `localhost:8081`
- Python ML worker (no external port needed)

### Manual Setup (Development)

**Backend:**
```bash
cd FintechApp
mvn clean install
mvn spring-boot:run
```

**ML Worker:**
```bash
cd python-ml-app
python -m venv ml-env
source ml-env/bin/activate  # or `ml-env\Scripts\activate` on Windows
pip install -r requirements.txt
python main.py
```

**Frontend:**
Open `frontend/dashboard.html` in your browser or serve it with any HTTP server.

## Configuration

### Environment Variables

Create a `.env` file in the root directory:

```env
POSTGRESQL_PASSWORD=your_postgres_password
REDIS_PASSWORD=your_redis_password
MAIL_PASSWORD=your_gmail_password
JWT_SECRET_KEY=your_secret_jwt_key
CLIENT_ID=your_google_oauth_client_id
CLIENT_SECRET=your_google_oauth_secret
```

### Database Setup

The system auto-creates tables via Hibernate DDL. Just provide the PostgreSQL credentials.

### API Port

Default port is `8081`. Change it in `application.properties`:
```properties
server.port=8082
```

## API Endpoints

### Public APIs (No Authentication)
- `POST /public/signup` - Register new user
- `POST /public/login` - Login and get JWT token
- `GET /auth/google/login?code=...` - Google OAuth login

### Protected APIs (Requires JWT)
- `POST /api/transactions` - Submit a transaction for fraud check
- `GET /api/transactions` - Get transaction history (paginated)
- `PUT /user` - Update user profile
- `DELETE /user` - Delete user account
- `GET /user` - Get user greetings with weather & quote of the day

### Documentation
- Swagger UI available at `http://localhost:8081/docs` after startup

## Real-Time Dashboard

Open the dashboard at `frontend/dashboard.html` (or serve via HTTP):

1. Enter your User ID
2. Click "Connect to Server"
3. Submit a transaction via the API
4. Watch the transaction status update in real-time

The dashboard shows:
- ✅ **Green** - Transaction approved
- ❌ **Red** - Transaction rejected (fraud detected)
- ⏳ **Yellow** - Pending

## ML Model Details

The system uses **Isolation Forest** for anomaly detection:
- Detects unusual transaction amounts
- Contamination rate: 10% (adjust in main.py)
- Trained on historical transaction patterns

The model runs in the Python worker and processes each transaction in real-time.

## Testing

Run unit tests:

```bash
cd FintechApp
mvn test
```

## Key Features

✨ **Real-Time Processing** - Sub-second fraud detection using Kafka streaming

🔔 **Live Notifications** - WebSocket updates push results instantly to users

🔐 **Multiple Auth Methods** - JWT + Google OAuth support

📧 **Alert Emails** - Suspicious transactions trigger email notifications

💾 **Caching** - Redis reduces database load for frequent queries

🎯 **Scalable** - Kafka allows horizontal scaling of ML workers

📊 **Swagger Docs** - Interactive API documentation built-in

## Troubleshooting

**Kafka not starting?**
- Ensure port 9092 is free
- Check Docker daemon is running
- Review docker-compose logs: `docker-compose logs kafka`

**Python worker not connecting?**
- Verify Kafka broker is healthy: `docker-compose logs kafka`
- Check ML container logs: `docker-compose logs python-ml-worker`
- Ensure bootstrap servers config matches

**Frontend can't connect?**
- Ensure Spring Boot is running on 8081
- Check browser console for CORS errors
- Verify WebSocket endpoint is correct in dashboard.html

**PostgreSQL connection issues?**
- Check credentials in `application.properties`
- Verify database exists and user has permissions
- Ensure connection URL is correct

## Deployment

### Docker Compose (Recommended for Production)
```bash
docker-compose -f docker-compose.yml up -d
```

### Kubernetes (Future Enhancement)
The services are containerized and can be deployed to K8s with minimal changes.

## Contributing

Feel free to submit issues and PRs. For major changes, open an issue first to discuss what you'd like to change.

## License

MIT License - feel free to use this for commercial projects.

## Contact & Support

- Email: parv10046@gmail.com
- GitHub Issues: For bug reports and feature requests

## Roadmap

- [ ] Add more sophisticated ML models (XGBoost, Neural Networks)
- [ ] Implement distributed caching strategy
- [ ] Add transaction history analytics dashboard
- [ ] Support for multiple currencies
- [ ] Mobile app for push notifications
- [ ] Integration with payment gateway APIs
- [ ] Advanced fraud patterns (card cloning, account takeover)

## Acknowledgments

Built with Spring Boot, Kafka, and scikit-learn. Special thanks to the open-source community for these amazing tools.

---

**Last Updated:** April 2026

**Status:** Active Development - Use with caution in production
