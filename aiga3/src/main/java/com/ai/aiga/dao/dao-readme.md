###spring data jpa 使用帮助文档收集
官方文档 : http://docs.spring.io/spring-data/jpa/docs/1.11.0.RELEASE/reference/html/#project

目前项目中使用的版本为: 1.11.0.RELEASE .

#### 根据业务复杂程度, 分为以下几个使用阶段

#### 0, 基础说明

使用spring data jpa来封装DAO层操作的目的, 是为了屏蔽那些比较常用, 简单的sql操作. 要使用spring data jpa, 就必须遵守他需要规则. 基础配置暂不在文档中说明.

基础规则:
	1, DAO层, 只需要定义接口和注解就可以, 至于方法的使用, 由spring data jpa去实现
	2, DAO层的接口, 必须继承 spring data jpa的核心接口org.springframework.data.repository.Repository<T, ID extends Serializable>或其子类.
	3, 继承父类, 注意, 需要域对象和主键.
	

#### 1, 使用 spring data jpa 已定义好的接口中的方法

接口有 : 
org.springframework.data.repository.CrudRepository 
org.springframework.data.repository.PagingAndSortingRepository	
org.springframework.data.jpa.repository.JpaRepository

这几个, 基础的接口已经具备了, 基础的CRUD, 分页, 排序. 具体使用的时候, 自行测试.


	//保存, 也可以用于更新
	<S extends T> S save(S entity);
	<S extends T> Iterable<S> save(Iterable<S> entities);
	Iterable<T> findAll(Sort sort);
	Page<T> findAll(Pageable pageable);

	//查找
	T findOne(ID id);
	boolean exists(ID id);
	Iterable<T> findAll();
	Iterable<T> findAll(Iterable<ID> ids);
	long count();

	//删除
	void delete(ID id);
	void delete(T entity);
	void delete(Iterable<? extends T> entities);
	void deleteAll();
	
#### 2, 通过定义方法名字, 入参, 返回值 来说明你需要查询的方法.

		
	