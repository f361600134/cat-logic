package com.cat.server.rank;

import com.cat.api.module.rank.assist.ISorter;
import com.cat.server.utils.Pair;

/**
 * 排行数据<br>
 * 某个排行榜中的单条数据
 * 
 * @author hdh
 *
 */
public class RankingData implements Comparable<RankingData>, Cloneable, ISorter {

    /**
     * id<br>
     * 玩家/联盟等的对象的id
     */
    private long id;

    /**
     * 额外参数<br>
     * 该id下 实际用于比较的额外参数<br>
     * 如随从id
     */
    private int parameter;

    /**
     * 排行榜要比较的值
     */
    private long value;

    /**
     * 排行榜要比较的第二个值
     */
    private long value2;

    /**
     * 更新时间
     */
    private long refreshTime;

    private transient Pair<Long, Integer> key;

    public RankingData() {
    }

    public RankingData(long id, int parameter, long value, long refreshTime) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
        this.refreshTime = refreshTime;
    }

    public RankingData(long id, int parameter, long value, long value2, long refreshTime) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
        this.value2 = value2;
        this.refreshTime = refreshTime;
        getKey();//默认生成key
    }

    /**
     * key 单个排行榜中唯一的key
     * 
     * @return
     */
    public Pair<Long, Integer> getKey() {
        if (key == null) {
            key = buildKey(id, parameter);
        }
        return key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public long getValue2() {
        return value2;
    }

    public void setValue2(long value2) {
        this.value2 = value2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + parameter;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RankingData other = (RankingData) obj;
        if (id != other.id)
            return false;
        if (parameter != other.parameter)
            return false;
        return true;
    }

    @Override
    public int compareTo(RankingData o) {
        long otherValue = o.getValue();
        if (value != otherValue) {
            return Long.compare(otherValue, value);
        }
        long otherValue2 = o.getValue2();
        if (value2 != otherValue2) {
            return Long.compare(otherValue2, value2);
        }
        long otherTime = o.getRefreshTime();
        if (refreshTime != otherTime) {
            return Long.compare(refreshTime, otherTime);
        }
        return Long.compare(id, o.getId());
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e) {
        }
        return null;
    }

    public static Pair<Long, Integer> buildKey(long id, int parameter) {
        return Pair.of(id, parameter);
    }

	@Override
	public long getFirstOrder() {
		return value;
	}

	@Override
	public long getSecondOrder() {
		return value2;
	}

	@Override
	public long getThirdOrder() {
		return refreshTime;
	}

	@Override
	public long getFourthOrder() {
		return getKey().hashCode();
	}

	@Override
	public String toString() {
		return "RankingData [id=" + id + ", parameter=" + parameter + ", value=" + value + ", value2=" + value2
				+ ", refreshTime=" + refreshTime + ", key=" + getKey() + "]";
	}
	
}
