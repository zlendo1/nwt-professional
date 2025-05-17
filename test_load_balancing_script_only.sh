#!/bin/bash

# Definišite URL adrese i portove vaših pokrenutih instanci
INSTANCE1_URL="http://localhost:8081/api/info" # Prilagodite endpoint ako je drugačiji
INSTANCE1_PORT="8081"
INSTANCE2_URL="http://localhost:8082/api/info" # Prilagodite endpoint ako je drugačiji
INSTANCE2_PORT="8082"

# Brojači
INSTANCE1_COUNT=0
INSTANCE2_COUNT=0
ERROR_COUNT=0
TOTAL_TIME=0.0
REQUESTS=100

echo "Sending $REQUESTS requests, alternating between instances..."
echo "Target 1: $INSTANCE1_URL (Port $INSTANCE1_PORT)"
echo "Target 2: $INSTANCE2_URL (Port $INSTANCE2_PORT)"

for (( i=1; i<=REQUESTS; i++ ))
do
    TARGET_URL=""
    # Round Robin: neparni zahtjevi idu na instancu 1, parni na instancu 2
    if (( i % 2 != 0 )); then
        TARGET_URL=$INSTANCE1_URL
        EXPECTED_PORT=$INSTANCE1_PORT
    else
        TARGET_URL=$INSTANCE2_URL
        EXPECTED_PORT=$INSTANCE2_PORT
    fi

    echo -n "Req $i -> $TARGET_URL ... " # Ispisuje bez nove linije

    # Pošalji zahtjev i izmjeri vrijeme (-w dodaje vrijeme na kraj)
    # -s (silent) da ne prikazuje progress bar
    RESPONSE=$(curl -s -w "\n%{time_total}" $TARGET_URL)

    # Izdvoji tijelo odgovora (sve osim zadnje linije)
    BODY=$(echo "$RESPONSE" | sed '$d')
    # Izdvoji vrijeme (zadnja linija)
    REQ_TIME=$(echo "$RESPONSE" | tail -n 1)

    # Provjeri da li je vrijeme validan broj
    if [[ $REQ_TIME =~ ^[0-9]+([.][0-9]+)?$ ]]; then
        TOTAL_TIME=$(echo "$TOTAL_TIME + $REQ_TIME" | bc -l)
    else
        echo "Warning: Could not parse request time '$REQ_TIME' for request $i."
        REQ_TIME=0
    fi

    # Pokušaj izvući port iz odgovora (prilagodite 'grep' ako je format poruke drugačiji)
    RESPONDING_PORT=$(echo "$BODY" | grep -oP 'port:\s*\K\d+')

    if [[ "$RESPONDING_PORT" == "$INSTANCE1_PORT" ]]; then
        ((INSTANCE1_COUNT++))
        echo "OK (Instance $INSTANCE1_PORT, Time: ${REQ_TIME}s)"
    elif [[ "$RESPONDING_PORT" == "$INSTANCE2_PORT" ]]; then
        ((INSTANCE2_COUNT++))
        echo "OK (Instance $INSTANCE2_PORT, Time: ${REQ_TIME}s)"
    else
        ((ERROR_COUNT++))
        # Ispisuje samo dio tijela odgovora u slučaju greške
        echo "FAILED or Unknown Port (Time: ${REQ_TIME}s) -> Response: $(echo $BODY | head -c 100)..."
    fi

    # sleep 0.05 # Opciona mala pauza, 50ms
done

echo "----------------------------------------------------"
echo "Load Balancing Simulation Results (Script Only):"
echo "----------------------------------------------------"
echo "Requests sent to Instance on Port $INSTANCE1_PORT: $INSTANCE1_COUNT"
echo "Requests sent to Instance on Port $INSTANCE2_PORT: $INSTANCE2_COUNT"
echo "Errors / Unknown responses: $ERROR_COUNT"
echo "----------------------------------------------------"

SUCCESSFUL_REQUESTS=$((INSTANCE1_COUNT + INSTANCE2_COUNT))

if (( SUCCESSFUL_REQUESTS > 0 )); then
     AVG_TIME=$(echo "scale=4; $TOTAL_TIME / $SUCCESSFUL_REQUESTS" | bc -l)
     echo "Total time for $SUCCESSFUL_REQUESTS successful requests: ${TOTAL_TIME}s"
     echo "Average successful request time: ${AVG_TIME}s"
else
     echo "No successful requests to calculate average time."
fi
echo "===================================================="