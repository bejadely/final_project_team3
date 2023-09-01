function handleClick(data) {
		
		var postId = data.postId;
	    var nowPage = data.clickedPage;

		let obj = { postId: postId, 
					nowPage: nowPage };
		
    	
		 $.ajax({
            url: 'ajaxCartList',
            type: 'post',
            data:obj
            
        }).done(data => {
        	paging(data.paging);
            displayData(data.cartList);
        }).fail(reject => console.log(reject));
    }

    // 클릭 이벤트 핸들러 자동 실행
    $('#pakage').click(function() {
    	var postId = 'PKG';
        var data = { postId: postId }
        handleClick(data);
        
    });

    $('#mul').click(function() {
    	var postId = 'SPE';
        var clickedPage = '1';
        var data = { postId: postId }
        handleClick(data);
    });
    
    // 데이터를 표시하는 함수
	function displayData(data) {
		 var html = ""; // 빈 문자열로 초기화
		    var lmth = "";
		    var price = "";
		    var thead = $("thead");
		    var tbody = $("tbody");
		    var priceBody = $("#price");
		    thead.empty();
		    tbody.empty(); // tbody 내용 초기화
	    
	    lmth += "<tr>" + 
		"<th ><input type='checkbox' id='cbx_chkAll' /></th>" +
		"<th scope='col' class='th-num'>상품사진</th>" +
		"<th scope='col' class='th-num'>상품 명</th>" +
		"<th scope='col' class='th-num'>상품수량</th>" +
		"<th scope='col' class='th-num'>상품가격</th>" +
		"<th >삭제</th>"+
		"</tr>";
	
	    // 가져온 데이터를 순회하면서 처리	
	    $.each(data, (index, cartItem) => {
	    		html += "<tr>" +
	            "<td><input type='checkbox' name='chk' data-cartid='" + cartItem.postId + "' /></td>" +
	            "<td>"+"<input type='hidden' class='cartId' value='" + cartItem.cartId + "' />" + cartItem.cartName + "</td>" +
	            "<td class='orderName'>" + cartItem.cartName + "</td>" +
	            `<td>
				    <button type="button" class="quantity-btn decrement-btn">-</button>
				    <input type="text" class="quantity-input" name="productAmount" value="${cartItem.quantity}">
				    <button type="button" class="quantity-btn increment-btn">+</button>
				</td>` +
	            "<td>" + cartItem.price + "</td>" +
	            "<td><button class='delete-btn'>삭제</button></td>" +
	            "</tr>";	        
	    });
	    
	    price = `<label>총 주문 금액 : </label>
			 <input type="text" id="result" readonly="readonly">`
	    
	    // tbody 내용을 업데이트
	    thead.html(lmth);
	    tbody.html(html);
	    priceBody.html(price);
	}
   
	    // 이벤트 핸들러 연결
	    $("body").on("click", ".quantity-btn", function(e) {
	    	e.stopImmediatePropagation();
	    	var th = $(this);
	    	var postId = th.parent().parent().find("td:eq(0)").find("input[name='chk']").data("cartid");
	        var input = th.siblings(".quantity-input");
	        var currentValue = parseInt(input.val());
	        
	        
	        if ($(this).hasClass("increment-btn")) {
	            input.val(currentValue + 1);
	            
	        } else if ($(this).hasClass("decrement-btn")) {
	            if (currentValue > 1) {
	                input.val(currentValue - 1);
	                
	            }
	        }
	        var result = input.val();
	        
	        
 	        $.ajax({
	            url: 'updateQuantity',
	            type: 'post',
	            data: { postId : postId,
	            		quantity : result
	            	  }
	            
	        }).done(data => {
	        
	        	updateTotalAndPrice(data.quantity)
	        		            
	        }).fail(reject => console.log(reject));
	    }); 
	
    
    function paging(data){
    	var html = "";
    	var div = $("#paging");
    	
    	div.empty();
    	
    	if (data.startPage != 1) {
            html += '<a><<</a>';
            html += '<a>&lt;</a>';
        }

		
        for(var p = data.startPage ; p <= data.endPage; p ++){
        	if (p == data.nowPage) {
                html += '<b>' + p + '</b>';
            } else {
                html += '<a>' + p + '</a>';
            }
        }

        if (data.endPage != data.lastPage) {
            html += '<a>&gt;</a>';
        }

        if (data.endPage != data.lastPage) {
        	html += '<a><input type="hidden" value="' + data.lastPage + '">>></a>';
        }
        
        div.html(html);
        
    }
    //페이징 버튼 클릭시 동작
    $("#paging").on("click", "a", function(event) {
        event.preventDefault(); // 기본 동작 방지 (링크 이동 X)
        
        var clickedPage = $(this).text(); // 클릭한 페이지 번호 가져오기
        var tbody = $("tbody")
        var second = tbody.children().children();
        var cartId = second.find("input[name='chk']").data("cartid");
        var postId = cartId.substring(0, 3); 
        //다음 페이지
        if(clickedPage == '>'){
        	var next = $(this).prev().text();
        	clickedPage = Number(next) + 1;
        } 
        
        //이전 페이지
        if(clickedPage == '<'){
        	var next = $(this).next().text();
        	clickedPage = Number(next) - 1;
        }
        
        //처음으로
        if(clickedPage == '<<'){
        	clickedPage = 1;
        }
        
        //끝으로
        if(clickedPage == '>>'){
        	var last = $(this).find('input').val();
        	clickedPage = last;
        } 
        
        var data = { postId: postId, clickedPage: clickedPage };
        
        handleClick(data); // 객체로 데이터를 함께 넘김
    });
    
      
	$("#pakage").trigger('click');
	
	//체크박스 활성화 시 총 금액 추가되는 부분	
function updateTotalAndPrice(data) {
    var result = 0;
    
    // 체크된 체크박스를 찾는 루프
    $("tbody input[type=checkbox]:checked").each(function() {
        var row = $(this).closest("tr");
        var quantity = row.find("input[type=text]").val();
        var price = parseFloat(row.find("td:eq(4)").text());
        var cartTotal = quantity * price;
        result += cartTotal;
    });

    // 결과를 텍스트 필드에 설정
    $("#result").val(result);
}

// 체크박스나 입력 필드의 변경 시에만 updateTotalAndPrice 호출
$("tbody input[type=checkbox], input[type=text]").on("change input", updateTotalAndPrice);
	
	var result = "";
    // 체크박스 전체 선택/해제 기능
    $("thead").on("click", "input[type=checkbox]", function() {
        if ($(this).is(":checked")) {
            $("input[name=chk]").prop("checked", true);
        } else {
            $("input[name=chk]").prop("checked", false);
        }
        
        updateTotalAndPrice();
    });

    // 각 체크박스의 클릭 이벤트 처리
    $("tbody").on("click", "input[type=checkbox]", function() {
    	updateTotalAndPrice();
        var total = $("input[name=chk]").length;
        var checked = $("input[name=chk]:checked").length;
        if (total != checked) {
            $("#cbx_chkAll").prop("checked", false);
        } else {
            $("#cbx_chkAll").prop("checked", true);
        }
        
    });
    
    // 삭제 버튼 클릭 시 선택된 항목 삭제
    $("body").on("click", ".delete-btn", function() {
        let row = $(this).closest("tr");
        let cartId = row.find("input[name='chk']").data("cartid");
        let url = "/cartDelete?postId=" + cartId;

        fetch(url)
            .then(response => response.text())
            .then(text => {
                row.remove();
                var page = $('#paging').find('b').text();
                var postId = cartId.substring(0, 3);
                var tbody = $("tbody");
                var length = tbody.children().length
                
                if(page != 1){
                    if(length == 0){
                    	page = Number(page) -1;
                    }
                }
                var data = { postId: postId, clickedPage: page };
                handleClick(data);
            });
    });
    
    // 삭제 버튼 클릭 시 선택된 항목 삭제
    $("#delBtn").click(function() {
    let ckb = $('tbody input[type=checkbox]:checked');

    ckb.each(function() {
        let check = $(this);
        let id = check.val();
        let cartId = check.data("cartid"); // Retrieve cartId
        let url = "/cartDelete?postId=" + cartId; // Use 'cartId'


        fetch(url)
            .then(response => response.text())
            .then(text => {
                check.parent().parent().remove();
                var page = $('#paging').find('b').text();
                var postId = cartId.substring(0, 3);
                var tbody = $("tbody");
                var length = tbody.children().length
                if(page != 1){
                    if(length == 0){
                    	page = Number(page) -1;
                    }
                }
                var data = { postId: postId, clickedPage: page };
                handleClick(data);
                     
            });
    });
  }); 
	 
  	$("#buyBtn").click(function(){
  		var totalAmount =0;
  		var quantity = 0;
  		var orderName= null;
  		var orderElements = [];
  		var th = $(this);
	    var cartIdElements = [];
	    var postId;
  		// 체크된 체크박스를 찾는 루프
	    $("tbody input[type=checkbox]:checked").each(function() {
	        var row = $(this).closest("tr");
	        var count = parseInt(row.find("input[type=text]").val());
	        var price = parseFloat(row.find("td:eq(4)").text());
	        var value = row.find("input[type=hidden]").val() 
	        var cartTotal = count * price;
			orderElements.push(row.find("td:eq(2)").text()); // 값을 배열에 추가	       
	        console.log(orderElements);
	        console.log(value);
	        cartIdElements.push(value);
	        totalAmount += cartTotal;
	        quantity += count;
	    });	
	    
	    	if(orderElements.length == 1) {
				orderName = orderElements[0];		
			}
			else{
				orderName = orderElements[0] + ' 외 ' + (orderElements.length - 1) + '건';
			}		
			

			
	        console.log(quantity);
	        console.log(orderName);
	        console.log(totalAmount);
	        console.log(cartIdElements);
	        
	     $.ajax({
	     	type:'post',
	     	url:'payment/ready',
	     	data:{
	     		totalAmount: totalAmount,
	     		orderName : orderName,
	     		quantity : quantity,
	     		postId: cartIdElements.join(',')
	     		
	     	},
	     	success:function(response){
	     		location.href = response.next_redirect_pc_url
	     	}
	       	
	     }); 
	        
  	});
  	
 
  	
  	