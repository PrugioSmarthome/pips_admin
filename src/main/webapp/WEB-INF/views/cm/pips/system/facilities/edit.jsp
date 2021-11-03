<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.15/lodash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>
<script type="text/javascript">
    function drawDataTable (drawData){
        $('#table1').DataTable({
            lengthChange: false,
            searching: false,
            ordering: false,
            info: false,
            paging: false,
            data: drawData,
            columns:[
                {data: '대공종'},
                {data: '업체명'},
                {data: '공사내역'},
                {data: '성명'},
                {data: '휴대폰'},
                {data: '사무실'},
                {data: '팩스'}
            ]
        });
    }

    function makeJsonData (parsingData) {
        var nestData = nest(parsingData, ['구분', '대공종', '업체명', '공사내역']);

        jsonData = convertJsonData(nestData);


        $("#facilityInfoList").val(JSON.stringify(jsonData));
    }

    var nest = function (seq, keys) {
      if (!keys.length)
        return seq;
      var first = keys[0];
      var rest = keys.slice(1);
      return _.mapValues(_.groupBy(seq, first), function (value) {
        return nest(value, rest)
      });
    };

    function convertJsonData (nestData) {
      var array = [];
      var obj = {
        fa: "",
        tw: "",
        biz: "",
        con: "",
        addressList: []
      }
      var addObj = {
        per: "",
        mp: "",
        fax: "",
        offc: ""
      }
      for (var prop1 in nestData) {

        for (var prop2 in nestData[prop1]) {

          for (var prop3 in nestData[prop1][prop2]) {

            for (var prop4 in nestData[prop1][prop2][prop3]) {

              obj.fa = prop1;
              obj.tw = prop2;
              obj.biz = prop3;
              obj.con = prop4;

              for (var prop5 in nestData[prop1][prop2][prop3][prop4]) {
                addObj.per = nestData[prop1][prop2][prop3][prop4][prop5]["성명"]
                addObj.mp = nestData[prop1][prop2][prop3][prop4][prop5]["휴대폰"]
                addObj.offc = nestData[prop1][prop2][prop3][prop4][prop5]["사무실"]
                addObj.fax = nestData[prop1][prop2][prop3][prop4][prop5]["팩스"]

                obj.addressList.push(JSON.parse(JSON.stringify(addObj)))
                addObj = {
                  per: "",
                  mp: "",
                  offc: "",
                  fax: ""
                }
              }

              array.push(JSON.parse(JSON.stringify(obj)));

              obj = {
                fa: "",
                tw: "",
                biz: "",
                con: "",
                addressList: []
              }
            }
          }
        }
      }

      return array;
    }

     function save(){
        $("#facilityInfo").submit();
     }
</script>
<body>
<form:form action="/cm/system/facility/editFacilityInfoAction" id="facilityInfo" name="facilityInfo" commandName="facilityInfo" method="post">
<input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="facilityInfoList" name="facilityInfoList" value="<c:out value="${facilityInfoList}"/>" style="display:none;"/>
    <div id="container">
        <div class="container">
        <div class="top_area">
                <h2 class="tit">시설업체 신규등록</h2>
            <ul class="location">
                <li>시설업체 관리</li>
                <li>시설업체 정보 관리</li>
                <li>시설업체정보 등록</li>
            </ul>
            </div>
            <div class="table_wrap2">
                <table class="table2">
                    <colgroup>
                        <col style="width:150px"/>
                        <col />
                        <col style="width:150px"/>
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>엑셀파일</th>
                            <td colspan="3">
                                <div class="custom-file">
                                    <input type="file" class="custom-file-input" id="upload" value="파일찾기" aria-describedby="inputGroupFileAddon01" name="files[]" accept=".xls, .xlsx">
                                    <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                </div>
                                <script type="text/javascript">
                                    $('#upload').on('change',function(){
                                        var tableDestroy = $('#table1').DataTable();
                                        tableDestroy.destroy();
                                        var fileName = $(this).val();
                                        $(this).next('.custom-file-label').html(fileName);
                                    })
                                </script>
                                <script type="text/javascript">
                                    var ExcelToJSON = function() {

                                    this.parseExcel = function(file) {
                                    var reader = new FileReader();

                                    reader.onload = function(e) {
                                      var data = e.target.result;
                                      var workbook = XLSX.read(data, {
                                        type: 'binary'
                                      });
                                      workbook.SheetNames.forEach(function(sheetName) {
                                        // Here is your object
                                        var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                                        var json_object = JSON.stringify(XL_row_object);
                                        var data = new Object();
                                        data = JSON.parse(json_object);

                                        //jQuery('#xlx_json').val( json_object );
                                        makeJsonData(data);
                                        drawDataTable(data);
                                      })
                                    };

                                    reader.onerror = function(ex) {

                                    };

                                    reader.readAsBinaryString(file);
                                  };
                              };

                              function handleFileSelect(evt) {
                                var files = evt.target.files; // FileList object
                                var xl2json = new ExcelToJSON();
                                xl2json.parseExcel(files[0]);
                              }

                             document.getElementById('upload').addEventListener('change', handleFileSelect, false);
                            </script>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="table_wrap2">
                <table class="table" id="table1">
                    <thead>
                        <tr>
                            <th scope="col">대공종</th>
                            <th scope="col">업체명</th>
                            <th scope="col">공사내역</th>
                            <th scope="col">성명</th>
                            <th scope="col">휴대폰</th>
                            <th scope="col">사무실</th>
                            <th scope="col">팩스</th>
                        </tr>
                    </thead>
                    <tbody style="text-align:center" id="databody">

                    </tbody>
                </table>
            </div>
            <div class="tbl_btm_area2">
                <div class="right_area">
                    <input type="button" class="btn btn-brown" onclick="back();" value="취소"/>
                    <input type="button" class="btn btn-brown" onclick="save();" value="저장"/>
                </div>
            </div>
        </div>
    </div>
</form:form>
</body>
</html>