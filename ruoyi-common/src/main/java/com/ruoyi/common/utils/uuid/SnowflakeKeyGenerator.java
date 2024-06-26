package com.ruoyi.common.utils.uuid;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;

@Slf4j
public final class SnowflakeKeyGenerator {

    public static final long EPOCH;

    private static final long SEQUENCE_BITS = 12L;

    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    private static long workerId;

    private static int maxTolerateTimeDifferenceMilliseconds = 10;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    private byte sequenceOffset;

    private long sequence;

    private long lastMilliseconds;

    /**
     * Set work process id.
     *
     * @param workerId work process id
     */
    public static void setWorkerId(final long workerId) {
        if (workerId < 0L || workerId >= WORKER_ID_MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        SnowflakeKeyGenerator.workerId = workerId;
    }

    /**
     * Set max tolerate time difference milliseconds.
     *
     * @param maxTolerateTimeDifferenceMilliseconds max tolerate time difference milliseconds
     */
    public static void setMaxTolerateTimeDifferenceMilliseconds(final int maxTolerateTimeDifferenceMilliseconds) {
        SnowflakeKeyGenerator.maxTolerateTimeDifferenceMilliseconds = maxTolerateTimeDifferenceMilliseconds;
    }

    /**
     * Generate key.
     *
     * @return key type is @{@link Long}.
     */
    public synchronized Long generateKey() {
        long currentMilliseconds = System.currentTimeMillis();
        if (waitTolerateTimeDifferenceIfNeed(currentMilliseconds)) {
            currentMilliseconds = System.currentTimeMillis();
        }
        if (lastMilliseconds == currentMilliseconds) {
            if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
                currentMilliseconds = waitUntilNextTime(currentMilliseconds);
            }
        } else {
            vibrateSequenceOffset();
            sequence = sequenceOffset;
        }
        lastMilliseconds = currentMilliseconds;
        return ((currentMilliseconds - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    @SneakyThrows
    private boolean waitTolerateTimeDifferenceIfNeed(final long currentMilliseconds) {
        if (lastMilliseconds <= currentMilliseconds) {
            return false;
        }
        long timeDifferenceMilliseconds = lastMilliseconds - currentMilliseconds;
        if (timeDifferenceMilliseconds >= maxTolerateTimeDifferenceMilliseconds) {
            log.error(String.format("Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", lastMilliseconds, currentMilliseconds));
            //throw new IllegalStateException(String.format("Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", lastMilliseconds, currentMilliseconds));
        }
        Thread.sleep(timeDifferenceMilliseconds);
        return true;
    }

    private long waitUntilNextTime(final long lastTime) {
        long result = System.currentTimeMillis();
        while (result <= lastTime) {
            result = System.currentTimeMillis();
        }
        return result;
    }

    private void vibrateSequenceOffset() {
        sequenceOffset = (byte) (~sequenceOffset & 1);
    }

    public static void main(String[] args) {
        SnowflakeKeyGenerator s = new SnowflakeKeyGenerator();
        System.out.println(s.generateKey());
    }
}