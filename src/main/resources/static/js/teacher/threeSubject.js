var classId=100000001; //当点击index.html中的详细页面时，显示详细页面,通过index.html把值传过来
// var studentId=10000000141;   //登陆之后通过cookie将登录信息取出来

//layui中的数据表格

var threeSubject_test="test1";
var threeSubject_Stugrade=new Array();  //存储学生成绩
//监听下拉框值变化事件，注意layui中不能使用js中的onChange进行监听，使用form.on方法加过滤器
layui.use(['layer', 'form'], function () {
    var layer = layui.layer;
    var form = layui.form();
    //console.log("form is: "+form);
    //监听表单中的考试名称
    form.on('select(testSelect)', function (data) {
        alert(data.value);
        threeSubject_test=data.value;
        //allSubpt_getJson(); //下拉框中的值发生变化，则刷新echarts
    });
});

$(function () { //页面初始化加载
    threeSubject_getGrade();
})

function threeSubject_getGrade() {
    var URL="http://www.overlove.xin/ssm/studentrank?test=test1&classid=100000001";
    var temp_stunumber;var temp_stuname;var temp_stuscore;var temp_rank; //存储从服务器返回的学号、姓名、班级、排名
    console.log("start to empty table");
    $("#threegrade_table  tr:not(:first)").empty("");  //清除除首行外的所有行

    $.ajax({
        url: "http://www.overlove.xin/ssm/studentrank?test="+threeSubject_test+"&classid="+classId,//请求url
        type: "GET",	//请求类型  post|get
        // data : "key=value&key1=value2",	//后台用 request.getParameter("key");
        dataType: 'json',//返回数据的 类型 text|json|html--
        crossDomain: true,
        async:false,
        success: function (json) {	//回调函数 和 后台返回的 数据
            $.each(json, function (i, n) {
                temp_stunumber=n.student_id;
                temp_stuname=n.student_name;
                temp_stuscore=n.sumscore;
                temp_rank=i+1;
                $("#threegrade_table").append("<tr>" +
                    "<td  style=\"text-align: center\">" +  temp_stunumber +"</td>"
                    +"<td style=\"text-align: center\">" +  temp_stuname +"</td>"
                    +"<td style=\"text-align: center\">" +  temp_stuscore +"</td>"
                    +"<td style=\"text-align: center\">" +  temp_rank +"</td>"
                    + "</tr style=\"text-align: center\">");

                //$("#pointSelect").append("<option value=" + temp + ">" + temp + "</option>")
                //console.log("question_kownledge:"+n.point);  //题目类型获取成功
                // subjectKnowPoints.push(temp);
                // renderForm();  //重新渲染表单
            });
        }
    });


}

function searchUrl(){   //点击搜索按钮，判断两个下拉框的状态进行选择调用不同的函数(向服务器请求数据)
    alert("search been clicked!");
    //questionShowImg_getJson00();
    console.log("test :"+threeSubject_test);
    //通过判断两个下拉框中的值，调用不同方法
    threeSubject_getGrade();

}
