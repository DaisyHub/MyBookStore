# MyBookStore
是一个功能比较完善的图书商城项目，用了几天写完，练练手
使用MyEclipse，数据库使用MySql,经测试可用。
这个项目的主要难点:
  1.订单的显示：
    因为在'我的订单'中，有很多的订单条目，而订单条目下又有一本及以上的书籍信息，所以在声明Order和OrderItem的成员变量时要注意；
    再一个就是在从数据库查询订单数据时，因为所有的信息并不是放在一张表中，所以无法一次性的查询出订单的所有数据，而是使用循环嵌套，一层一层向外补充数据；
  2.在管理员页面：
      因为涉及到添加图书，其中包扩了图书的图片信息，jsp页面标签<input>中的属性type="file",此时无法使用我已经封装好的BaseServlet包，只能新建Servlet
      继承HttpServlet。而且不能再使用request.getParameters("")这个方法来获取表单的数据。至于如何获得，请看源码。。。
  3...待续
