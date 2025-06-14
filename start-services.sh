#!/bin/bash

PROJECT_DIRS=()
for dir in */; do
  if [[ -f "$dir/pom.xml" ]]; then
    PROJECT_DIRS+=("${dir%/}")
  fi
done

LOG_DIR=$(mktemp -d)
INSTALL_PIDS=()
RUN_PIDS=()

cleanup_logs() {
  rm -rf "$LOG_DIR"
}

install_projects() {
  echo "Installing all projects in parallel..."
  for dir in "${PROJECT_DIRS[@]}"; do
    (
      mvn -f "$dir/pom.xml" clean install -DskipTests >"$LOG_DIR/$dir.log" 2>&1
      echo $? >"$LOG_DIR/$dir.status"
    ) &
    INSTALL_PIDS+=($!)
  done

  for pid in "${INSTALL_PIDS[@]}"; do
    wait "$pid"
  done

  had_errors=0
  for dir in "${PROJECT_DIRS[@]}"; do
    if grep -q "^\[ERROR\]" "$LOG_DIR/$dir.log"; then
      echo "Errors in $dir:"
      grep "^\[ERROR\]" "$LOG_DIR/$dir.log"
      echo
      had_errors=1
    fi
  done

  if [[ $had_errors -eq 0 ]]; then
    echo "All ok"
  fi

  cleanup_logs
}

run_projects() {
  echo "Starting all Spring Boot services in parallel..."
  for dir in "${PROJECT_DIRS[@]}"; do
    (
      cd "$dir"
      mvn spring-boot:run
    ) &
    RUN_PIDS+=($!)
  done

  terminate() {
    echo -e "\nTermination signal received. Stopping all services..."
    for pid in "${RUN_PIDS[@]}"; do
      kill "$pid" 2>/dev/null
    done
    for pid in "${RUN_PIDS[@]}"; do
      wait "$pid" 2>/dev/null
    done
    echo "All services stopped. Exiting."
    exit 0
  }

  trap terminate SIGINT SIGTERM

  for pid in "${RUN_PIDS[@]}"; do
    wait "$pid"
  done
}

case "$1" in
  install)
    install_projects
    ;;
  start)
    run_projects
    ;;
  *)
    echo "Usage: $0 {install|start}"
    exit 1
    ;;
esac
