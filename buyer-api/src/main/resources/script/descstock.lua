local key = KEYS[1];  ---key
--local subNum = tonumber(ARGV[1]);  ---value
local surplusStock = tonumber(redis.call('get', key));   ---使用get命令获取key的value值  剩余库存
if (surplusStock <= 0) then
    return 0    ---  剩余库存<=0  return  0
else
    redis.call('incrby', KEYS[1], -1)
    return 1  --- 扣减成功返回 1
end
