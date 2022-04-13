package cn.wrxdark.modules.depositRecord.mapper;

import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.depositRecord.entity.dos.DepositRecord;
import cn.wrxdark.modules.member.entity.dos.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 刘宇阳
 * @since 2022-03-27
 */
@Mapper
@Repository
public interface DRecordMapper extends BaseMapper<DepositRecord> {

}
