
var classId=100000001; //当点击index.html中的详细页面时，显示详细页面,通过index.html把值传过来
var studentId=10000000141;
var oneSubjectTrend_subject="语文";
/*科目选定后，查询按钮的点击事件,用于显示数据*/
    function searchUrl(){   //点击搜索按钮，判断两个下拉框的状态进行选择调用不同的函数(向服务器请求数据)
        alert("search been clicked!");
        //questionShowImg_getJson00();
        console.log("oneSubjectTrend_subject :"+oneSubjectTrend_subject);
        //通过判断两个下拉框中的值，调用不同方法
        oneSubjectTrend_getTrend_table();
        oneSubjectTrend_getTrend_echarts();
    }

    layui.use(['layer', 'form'], function () { //下拉框选择科目
        var layer = layui.layer;
        var form = layui.form();
        //console.log("form is: "+form);
        //监听表单中的科目
        form.on('select(subjectSelect)', function (data) {
            alert(data.value);
            oneSubjectTrend_subject=data.value;
            //oneSubjectTrend_getTrend(); //下拉框中的值发生变化，则刷新echarts
        });
    });


$(function (){ //页面初始化加载
        oneSubjectTrend_getTrend_table(); //加载表格
        oneSubjectTrend_getTrend_echarts(); //渲染echarts
    })

    /*表格展示单科平均分的走势情况*/
    function oneSubjectTrend_getTrend_table() {
        console.log("table start:--- ");
        var URL="http://www.overlove.xin/ssm/pastlessonavg?id=100000001&lesson=语文";
        // allSubject_Stugrade.splice(0,allSubject_Stugrade.length); //清空数组
        var temp_table_testname;var temp_table_avgscore;var temp_table_rank; //存储从服务器返回的学号、姓名、班级、排名
        console.log("start to empty table");
        $("#oneSubjectTrend_table  tr:not(:first)").empty("");  //清除除首行外的所有行

        $.ajax({
            url: "http://www.overlove.xin/ssm/pastlessonavg?id="+classId+"&lesson="+oneSubjectTrend_subject,//请求url
            type: "GET",	//请求类型  post|get
            // data : "key=value&key1=value2",	//后台用 request.getParameter("key");
            dataType: 'json',//返回数据的 类型 text|json|html--
            crossDomain: true,
            async:false,
            success: function (json) {	//回调函数 和 后台返回的 数据
                $.each(json, function (i, n) {
                    temp_table_testname=n.test_name;
                    temp_table_avgscore=n.avgscore;
                    temp_table_rank=n.avgrank;
                $("#oneSubjectTrend_table").append("<tr>" +
                    "<td  style=\"text-align: center\">" +  temp_table_testname +"</td>"
                    +"<td style=\"text-align: center\">" +  temp_table_avgscore +"</td>"
                    +"<td style=\"text-align: center\">" +  temp_table_rank +"</td>"
                    + "</tr style=\"text-align: center\">");

                //$("#pointSelect").append("<option value=" + temp + ">" + temp + "</option>")
                //console.log("question_kownledge:"+n.point);  //题目类型获取成功
                // subjectKnowPoints.push(temp);
                // renderForm();  //重新渲染表单
                });
            }
        });
    }

    /*echarts绘制的那刻平均分的走势*/
    function oneSubjectTrend_getTrend_echarts(){
        console.log("echarts start:----");

        var testName=['test1','test2','test3','test4','test5','test6','test7'];  //所有的考试
        var rankList= new Array(7);  //7次考试班级科目平均分走势信息
        rankList.splice(0,rankList.length);
        var temp_echarts_avgsore;

        function getJson() {
            /*console.log("----------------");
            console.log("开始执行！");*/
            for(var j=0;j<7;j++) { //选定考试后循环获取5科的成绩
                // http://www.overlove.xin/ssm/classlessonscore?id=100000001&lesson=语文&test=test3
                //http://www.overlove.xin/ssm/studentrank?test=test4&classid=100000001
                var URL="http://www.overlove.xin/ssm/pastlessonavg?id=100000001&lesson=语文";
                console.log("testName: "+testName[j]);
                $.ajax({
                    url: "http://www.overlove.xin/ssm/pastlessonavg?id="+classId+ "&lesson="+oneSubjectTrend_subject,	//请求url
                    type: "GET",	//请求类型  post|get
                    // data : "key=value&key1=value2",	//后台用 request.getParameter("key");
                    dataType: 'json',//返回数据的 类型 text|json|html--
                    crossDomain: true,
                    async:false,
                    success: function (json) {	//回调函数 和 后台返回的 数据

                        $.each(json, function (i, n) {
                            //获取对象中属性为optionsValue的值
                            //testName.push(parseInt(n.test_name));
                            //subjectName.push(n.lesson_ame);
                            // if(n.student_id==studentId) {
                                // console.log("student_id:"+n.student_id);
                                //console.log("sumscore:"+n.sumscore);
                                //console.log("班级排名为: "+(i+1));

                            console.log("------");
                            temp_echarts_avgsore=n.avgscore;
                            console.log("temp_echarts_avgsore： "+temp_echarts_avgsore);
                            rankList.push(temp_echarts_avgsore);
                                // sumscoreList.push((n.sumscore));   //三科总成绩
                                //sumscoreList[i]=n.sumscore;
                                //   stuList.push(parseInt(n.student_id) - 10000000000); //学号三两位
                            // }
                        });
//}
                    }
                });
                //.done(rankList[j]=temp_echarts_avgsore);   //ajax执行完成后的回调函数

            }
        }


        getJson();
        console.log("开始打印rankList: ------");
        console.log("teh length of ranklist: "+rankList.length);
        for(var i=0;i<rankList.length;i++){
            console.log(rankList[i]+" ");
        }

        //基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('oneSubjectTrend_chart'));
         option = {
            /*title : {
                left:'center',
                text: '历次考试中班级语文成绩排名',
            },*/
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                data: testName,
            },
            yAxis: {
                type: 'value',
                data:rankList,
            },
            series: [{
                name:'平均分',
                data: rankList,
                type: 'line',
                smooth: true,
                itemStyle: {
                    normal: {
                        label: {
                            show: true, //开启显示
                            position: 'top', //在上方显示
                            textStyle: { //数值样式
                                color: 'black',
                                fontSize: 16
                                }
                        }
                    }
                },
            }]
        };
    // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);  //渲染区域
        window.addEventListener("resize", function () {
        myChart.resize();
        });
    }
