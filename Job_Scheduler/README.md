# Job Scheduler

A distributed job scheduler built with Java and Redis.

## Prerequisites
- Java 17
- WSL (Windows Subsystem for Linux)
- Redis (installed in WSL)

## Before Running the App

Every time you restart your PC, you need to start Redis first.

**1. Open WSL** (search "WSL" or "Ubuntu" in the Start menu)

**2. Start Redis:**
```bash
sudo service redis-server start
```

**3. Verify Redis is running:**
```bash
redis-cli ping
# Expected output: PONG
```

**4. Stop Redis (when done):**
```bash
sudo service redis-server stop
```

**5. Check Redis status:**
```bash
sudo service redis-server status
```

## Running the App
Run the `Main.java` file directly from IntelliJ.

## Useful Redis Commands (for debugging)
```bash
# Open Redis CLI
redis-cli

# View all keys
keys *

# Check a worker heartbeat
get worker:worker-1:heartbeat

# Check job queue
lrange job_queue 0 -1

# Clear everything
flushall
```
