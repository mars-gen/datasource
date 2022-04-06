
/*错题查询分析*/
var questionShow_subject="";
var questionShow_point="";






function searchUrl(){   //点击搜索按钮，判断两个下拉框的状态进行选择调用不同的函数(向服务器请求数据)
    //alert("search been clicked!");
    //questionShowImg_getJson00();
    console.log("questionShow_subject :"+questionShow_subject);
    //通过判断两个下拉框中的值，调用不同方法

    var searchfile=$("#seachfile").val();
    var searchdetails=$("#pointSelect").val();
    var searchuser=$("#seachuser").val();
    console.log(searchdetails);
    console.log(searchfile);
    $.ajax({//数据线不填充
        url: "/pan/getpathadmin",
        type: "GET",
        data: {
            type:questionShow_subject,
            username:searchuser,
            filename:searchfile,
            details:searchdetails,
        },
        traditional: true,//这里设置为true
        success: function(data) {

            //do sth...
            filldate(data);
        }
    });
   // filldate(data2[questionShow_subject]);
    console.log(questionShow_subject)

}

layui.use('form', function () {
    var form = layui.form;
});


/*由于展示只需学生选择科目或知识点就可以展示，4种情况*/

//监听下拉框值变化事件，注意layui中不能使用js中的onChange进行监听，使用form.on方法加过滤器
layui.use(['layer', 'form'], function () {
    var layer = layui.layer;
    var form = layui.form();
    //console.log("form is: "+form);
    //监听表单中的考试科目
    form.on('select(subjectSelect)', function (data) {
       // alert(data.value);
        questionShow_subject=data.value;

    });
});

layui.use(['layer', 'form'], function () {
    var layer = layui.layer;
    var form = layui.form();
    //console.log("form is: "+form);
    //监听表单中的考试名称
    form.on('select(pointSelect)', function (data) {
        alert(data.value);
        questionShow_point=data.value;
    });
});


$(function () {

    $("#testRightclick").contextMenu({   //测试按钮
        width: 110, // width
        itemHeight: 30, // 菜单项height
        bgColor: "#333", // 背景颜色
        color: "#fff", // 字体颜色
        fontSize: 12, // 字体大小
        hoverColor: "#fff", // hover字体颜色
        hoverBgColor: "#99CC66", // hover背景颜色
        target: function(ele) { // 当前元素--jq对象
            console.log(ele);
        },
        menu: [{ // 菜单项
            text: "新增",
            icon: "add.png",
            callback: function() {
                alert("新增");
            }
        },
            {
                text: "复制",
                icon: "copy.png",
                callback: function() {
                    alert("复制");
                }
            },
            {
                text: "粘贴",
                icon: "paste.png",
                callback: function() {
                    alert("粘贴");
                }
            },
            {
                text: "删除",
                icon: "del.png",
                callback: function() {
                    alert("删除");
                }
            }
        ]
    });



});




    function question_show_showDetails(obj) {
        var s=$(obj).parent().prev().prev().prev().prev().text();
        var name=$(obj).parent().prev().prev().prev().prev().prev().text();
        console.log(s);
        console.log(name);
        var url='http://www.overlove.xin/pan/food/'+name+'/'+s;
         window.open(url);
    }
    function question_show_Delete(obj) { //"删除按钮"删除错题图片

        var s=$(obj).parent().prev().prev().prev().prev().text();
        var username=$(obj).parent().prev().prev().prev().prev().prev().text();

        // var book_id = $(this).parent().parent().attr("data-book-id");
        // console.log($(this).parent().parent());
        // console.log(book_id);
        layer.confirm("你确定要删除么？",{btn:['YES','NO'],
        btn1:function (index) {
            console.log("yes");

            $.ajax({//数据线不填充
                url: "/pan/deletefileadmin",
                type: "POST",
                data: {
                    filename:s,
                    username:username,

                },
                traditional: true,//这里设置为true
                success: function(data) {
                    alert(data);
                    //do sth...
                    //filldate(data);
                }
            });
            layer.close(index);
            window.location.reload();
        },
        btn2:function (index) {
            console.log("no");
            layer.close(index)
        }
        });



    }
    function show_element (e) { //显示鼠标点击的元素
        renderForm();
        if(!e){
            var e=window.event;
        }

        //获取事件点击元素
        var targ = e.target;
        //获取元素名称
        var  tname=targ.tagName;
        console.log("clicked element is: "+tname);
    }


/*获取某一科目下知识点的类型，然后动态加载到知识点下拉框*/
function renderForm(){  //表单重新渲染方法，不然下拉框中添加的新的option子项加载出来
    layui.use('form', function(){
        var form = layui.form();//高版本建议把括号去掉，有的低版本，需要加()
        form.render();
    });
}

/*function  renderEelemnt() {
    layui.use('element', function() {
        var element = layui.element;
        element.init();
    });
}*/





