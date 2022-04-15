-- 获取方法签名特征
local methodKey = KEYS[1]
-- 限流的阈值
local limit = tonumber(ARGV[1])
-- 限流的时间
local second = tonumber(ARGV[2])
-- 当前流量大小
local count = tonumber(redis.call('get', methodKey) or "0")

redis.log(redis.LOG_DEBUG, 'key is', methodKey)
-- 是否超出限流标准
if count >= limit then
    return false
else
    -- 执行计算器自加
    count = tonumber(redis.call('incr',methodKey))
    if count == 1 then
    -- 从第一次调用开始限流，设置对应键值的过期
        redis.call('expire',methodKey,second)
    end
    return true
end