package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdGenerator {

    // 位数分配
    private static final int SEQUENCE_BITS = 5;       // 序列号占用 5 位
    private static final int MACHINE_ID_BITS = 5;     // 标识 ID 占用 5 位
    private static final int TIMESTAMP_BITS = 22;     // 时间戳占用 22 位

    // 最大值计算
    private static final int MAX_SEQUENCE = ~(-1 << SEQUENCE_BITS);
    private static final int MAX_MACHINE_ID = ~(-1 << MACHINE_ID_BITS);

    // 位移量
    private static final int TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;
    private static final int MACHINE_ID_SHIFT = SEQUENCE_BITS;

    // 起始时间戳（秒级时间戳）
    private static final long START_TIMESTAMP = 1704067200L;

    // 成员变量
    private int sequence = 0;                      // 当前序列号
    private long lastTimestamp = -1L;              // 上次生成 ID 的时间戳
    private int machineId;                   // 标识 ID

    public IdGenerator(int machineId) {
        if (machineId < 0 || machineId > MAX_MACHINE_ID) {
            throw new IllegalArgumentException("Machine ID must be between 0 and " + MAX_MACHINE_ID);
        }
        this.machineId = machineId;
    }

    /**
     * 生成短 ID
     */
    public synchronized int nextId() {
        long currentTimestamp = getCurrentSecond();

        // 如果当前时间小于上次生成 ID 的时间，说明时钟回拨了
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id.");
        }

        // 如果在同一秒内，增加序列号
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // 序列号溢出，等待下一秒
                currentTimestamp = waitNextSecond(lastTimestamp);
            }
        } else {
            // 不同秒，重置序列号
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        // 缩短时间戳（从起始时间开始计算）
        int shortenedTimestamp = (int) (currentTimestamp - START_TIMESTAMP);

        // 组合 ID
        return (shortenedTimestamp << TIMESTAMP_SHIFT) |
                (machineId << MACHINE_ID_SHIFT) |
                sequence;
    }

    /**
     * 获取当前秒级时间戳
     */
    private long getCurrentSecond() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 等待下一秒
     */
    private long waitNextSecond(long lastTimestamp) {
        long timestamp = getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentSecond();
        }
        return timestamp;
    }

    /**
     * 生成id使用方法
     * @return 基于标识id返回不同的id
     */
    public Integer generator(int machineId){
        IdGenerator g = new IdGenerator(machineId);
        return g.nextId();
    }
}
