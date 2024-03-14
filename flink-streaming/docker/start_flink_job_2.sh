/opt/flink/bin/taskmanager.sh start \
    -Djobmanager.rpc.address=flink_job2 \
    -Dtaskmanager.numberOfTaskSlots=10 &&
/opt/flink/bin/standalone-job.sh start-foreground \
    --job-classname io.openlineage.flink.FromPostgresToTopicApplication \
    -Djobmanager.rpc.address=flink_job2 \
    -Dexecution.attached=true