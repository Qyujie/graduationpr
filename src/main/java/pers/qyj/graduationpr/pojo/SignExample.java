package pers.qyj.graduationpr.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table signs
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table signs
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table signs
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public SignExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table signs
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table signs
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(String value) {
            addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(String value) {
            addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThan(String value) {
            addCriterion("sign >", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThanOrEqualTo(String value) {
            addCriterion("sign >=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThan(String value) {
            addCriterion("sign <", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThanOrEqualTo(String value) {
            addCriterion("sign <=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLike(String value) {
            addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(String value) {
            addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(List<String> values) {
            addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(List<String> values) {
            addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(String value1, String value2) {
            addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(String value1, String value2) {
            addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andOidIsNull() {
            addCriterion("oid is null");
            return (Criteria) this;
        }

        public Criteria andOidIsNotNull() {
            addCriterion("oid is not null");
            return (Criteria) this;
        }

        public Criteria andOidEqualTo(Integer value) {
            addCriterion("oid =", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotEqualTo(Integer value) {
            addCriterion("oid <>", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThan(Integer value) {
            addCriterion("oid >", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThanOrEqualTo(Integer value) {
            addCriterion("oid >=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThan(Integer value) {
            addCriterion("oid <", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThanOrEqualTo(Integer value) {
            addCriterion("oid <=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidIn(List<Integer> values) {
            addCriterion("oid in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotIn(List<Integer> values) {
            addCriterion("oid not in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidBetween(Integer value1, Integer value2) {
            addCriterion("oid between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotBetween(Integer value1, Integer value2) {
            addCriterion("oid not between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andReidIsNull() {
            addCriterion("reid is null");
            return (Criteria) this;
        }

        public Criteria andReidIsNotNull() {
            addCriterion("reid is not null");
            return (Criteria) this;
        }

        public Criteria andReidEqualTo(Integer value) {
            addCriterion("reid =", value, "reid");
            return (Criteria) this;
        }

        public Criteria andReidNotEqualTo(Integer value) {
            addCriterion("reid <>", value, "reid");
            return (Criteria) this;
        }

        public Criteria andReidGreaterThan(Integer value) {
            addCriterion("reid >", value, "reid");
            return (Criteria) this;
        }

        public Criteria andReidGreaterThanOrEqualTo(Integer value) {
            addCriterion("reid >=", value, "reid");
            return (Criteria) this;
        }

        public Criteria andReidLessThan(Integer value) {
            addCriterion("reid <", value, "reid");
            return (Criteria) this;
        }

        public Criteria andReidLessThanOrEqualTo(Integer value) {
            addCriterion("reid <=", value, "reid");
            return (Criteria) this;
        }

        public Criteria andReidIn(List<Integer> values) {
            addCriterion("reid in", values, "reid");
            return (Criteria) this;
        }

        public Criteria andReidNotIn(List<Integer> values) {
            addCriterion("reid not in", values, "reid");
            return (Criteria) this;
        }

        public Criteria andReidBetween(Integer value1, Integer value2) {
            addCriterion("reid between", value1, value2, "reid");
            return (Criteria) this;
        }

        public Criteria andReidNotBetween(Integer value1, Integer value2) {
            addCriterion("reid not between", value1, value2, "reid");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIsNull() {
            addCriterion("arrival_date is null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIsNotNull() {
            addCriterion("arrival_date is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateEqualTo(Date value) {
            addCriterion("arrival_date =", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotEqualTo(Date value) {
            addCriterion("arrival_date <>", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThan(Date value) {
            addCriterion("arrival_date >", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThanOrEqualTo(Date value) {
            addCriterion("arrival_date >=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThan(Date value) {
            addCriterion("arrival_date <", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThanOrEqualTo(Date value) {
            addCriterion("arrival_date <=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIn(List<Date> values) {
            addCriterion("arrival_date in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotIn(List<Date> values) {
            addCriterion("arrival_date not in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateBetween(Date value1, Date value2) {
            addCriterion("arrival_date between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotBetween(Date value1, Date value2) {
            addCriterion("arrival_date not between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateIsNull() {
            addCriterion("depature_date is null");
            return (Criteria) this;
        }

        public Criteria andDepatureDateIsNotNull() {
            addCriterion("depature_date is not null");
            return (Criteria) this;
        }

        public Criteria andDepatureDateEqualTo(Date value) {
            addCriterion("depature_date =", value, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateNotEqualTo(Date value) {
            addCriterion("depature_date <>", value, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateGreaterThan(Date value) {
            addCriterion("depature_date >", value, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateGreaterThanOrEqualTo(Date value) {
            addCriterion("depature_date >=", value, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateLessThan(Date value) {
            addCriterion("depature_date <", value, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateLessThanOrEqualTo(Date value) {
            addCriterion("depature_date <=", value, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateIn(List<Date> values) {
            addCriterion("depature_date in", values, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateNotIn(List<Date> values) {
            addCriterion("depature_date not in", values, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateBetween(Date value1, Date value2) {
            addCriterion("depature_date between", value1, value2, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andDepatureDateNotBetween(Date value1, Date value2) {
            addCriterion("depature_date not between", value1, value2, "depatureDate");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table signs
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table signs
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}