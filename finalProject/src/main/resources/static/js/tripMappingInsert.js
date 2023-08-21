			// 마커를 담는 배열
            var markers = [];
            // 지도를 표시할 div
            var mapContainer = document.getElementById('map'), mapOption = {
               // 최초 생성된 지도의 중심좌표 
               // 맵핑, 선 긋기, db저장 완료되면 여행기록 등록시 선택한 지역의 좌표값이 들어가게 수정할 예정 
               center : new kakao.maps.LatLng(35.871159972672,
                     128.60183648521),
               // 최초 생성된 지도의 확대 레벨
               level : 7
            };

            // 지도 생성 
            var map = new kakao.maps.Map(mapContainer, mapOption);

            // 장소 검색 객체를 생성
            var ps = new kakao.maps.services.Places();

            // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성
            var infowindow = new kakao.maps.InfoWindow({
               zIndex : 1
            });
            
            $(document).ready(function() {
				// 승인 버튼 클릭 시 발생 이벤트 설정
				 function initMap() {
               	 	// 해당 페이지 실행 시 키워드 검색이 바로 실행되지 않도록 함수를 하나 추가
               		searchPlaces();
           		 }
			});

            // 키워드 검색을 요청하는 함수
            function searchPlaces() {

               var keyword = document.getElementById('keyword').value;

               if (!keyword.replace(/^\s+|\s+$/g, '')) {
                  alert('키워드를 입력해주세요!');
                  return false;
               }

               // 장소검색 객체를 통해 키워드로 장소검색을 요청
               ps.keywordSearch(keyword, placesSearchCB);
            }

            // 키워드 검색이 완료됐을 때 호출되는 콜백함수
            function placesSearchCB(data, status, pagination) {
               if (status === kakao.maps.services.Status.OK) {

                  // 정상적으로 검색이 완료 시 검색 목록과 마커를 표출
                  displayPlaces(data);

                  // 검색 결과 리스트 페이징
                  displayPagination(pagination);

               } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

                  alert('검색 결과가 존재하지 않습니다. 다시 검색 해주세요.');
                  return;

               } else if (status === kakao.maps.services.Status.ERROR) {

                  alert('다시 검색 해주세요.');
                  return;

               }
            }

            // 검색 결과 목록과 마커를 표출하는 함수
            function displayPlaces(places) {

               var listEl = document.getElementById('placesList'), 
                  menuEl = document.getElementById('menu_wrap'), 
                  fragment = document.createDocumentFragment(), 
                  bounds = new kakao.maps.LatLngBounds(), 
                  listStr = '';

               // 검색 결과 목록에 추가된 항목들을 제거
               removeAllChildNods(listEl);

               // 지도에 표시되고 있는 마커를 제거
               //removeMarker();

               for (var i = 0; i < places.length; i++) {

                  // 마커를 생성하고 지도에 표시
                  // 검색 결과 항목 Element를 생성
                  var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x), 
                                 itemEl = getListItem(i, places[i]);

                  // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
                  // LatLngBounds 객체에 좌표를 추가
                  bounds.extend(placePosition);

                  // 마커와 검색결과 항목에 mouseover 했을때
                  // 해당 장소에 인포윈도우에 장소명을 표시합니다
                  // mouseout 했을 때는 인포윈도우를 닫습니다
                  (function(marker, pname, praddress, paddress, plat, plng) {
                     kakao.maps.event.addListener(marker, 'mouseover', function() {
                        displayInfowindow(marker, pname);
                     });

                     kakao.maps.event.addListener(map, 'mouseout', function() {
                        infowindow.close();
                     });

                     // 리스트의 아이템을 클릭하면 정보들을 text 영역으로 전송 (hidden 사용가능)
                     itemEl.onclick = function() {
                        if (praddress) {
                           document.getElementById('fulladdress').value = "[" + pname + "]" + praddress;
                        } else {
                           document.getElementById('fulladdress').value = "[" + pname + "]" + paddress;
                        }

                        document.getElementById('pname').value = pname;
                        if (praddress) {
                           document.getElementById('paddress').value = praddress;
                        } else {
                           document.getElementById('paddress').value = paddress;
                        }
                        document.getElementById('latclick').value = plat;
                        document.getElementById('lngclick').value = plng;

                        var clickedMarkerPosition = new kakao.maps.LatLng(plat, plng);
                        var clickedMarker = new kakao.maps.Marker({
                           map : map,
                           position : clickedMarkerPosition
                        });

                        var clickedInfowindow = new kakao.maps.InfoWindow({
                           content : pname,
                           removable : true
                        });

                        kakao.maps.event.addListener(clickedMarker, 'click', function() {
                           clickedInfowindow.open(map, clickedMarker);
                        });

                        // Draw connecting line only if there is a previous marker
                        if (markers.length > 0) {
                           var prevMarker = markers[markers.length - 1];
                           var linePath = [ prevMarker.getPosition(), clickedMarkerPosition ];

                           var polyline = new kakao.maps.Polyline({
                              path : linePath,
                              strokeWeight : 3,
                              strokeColor : '#ff0000',
                              strokeOpacity : 0.7,
                              strokeStyle : 'solid'
                           });

                           polyline.setMap(map);
                        }

                        // Add the newly clicked marker to the markers array
                        markers.push(clickedMarker);
                     };

                     itemEl.onmouseover = function() {
                        displayInfowindow(marker, pname);
                     };

                     itemEl.onmouseout = function() {
                        infowindow.close();
                     };
                  })(marker, 
                     places[i].place_name,
                     places[i].road_address_name,
                     places[i].address_name, places[i].y,
                     places[i].x);

                  fragment.appendChild(itemEl);
               }

               // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
               listEl.appendChild(fragment);
               menuEl.scrollTop = 0;

               // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
               map.setBounds(bounds);
            }

            // 검색결과 항목을 Element로 반환하는 함수입니다
            function getListItem(index, places) {

               var el = document.createElement('li'), itemStr = '<span class="markerbg marker_'
                     + (index + 1)
                     + '"></span>'
                     + '<div class="info" style="cursor:pointer;">'
                     + '   <h5>' + places.place_name + '</h5>';

               if (places.road_address_name) {
                  itemStr += '    <span>' + places.road_address_name
                        + '</span>' + '   <span class="jibun gray">'
                        + places.address_name + '</span>';
               } else {
                  itemStr += '    <span>' + places.address_name + '</span>';
               }
               itemStr += '  <span class="tel">' + places.phone + '</span>';
               +'</div>';

               el.innerHTML = itemStr;
               el.className = 'item';

               return el;
            }


            // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
            function addMarker(position, idx, title) {
               var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
                           imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
               imgOptions = {
                  spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
                  spriteOrigin : new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                  offset : new kakao.maps.Point(13, 37)
               // 마커 좌표에 일치시킬 이미지 내에서의 좌표
               }, markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions), marker 
                           = new kakao.maps.Marker({
                              position : position, // 마커의 위치
                              image : markerImage
                           });

               marker.setMap(map); // 지도 위에 마커를 표출합니다
               markers.push(marker); // 배열에 생성된 마커를 추가합니다

               return marker;
            }

            // 검색결과 목록 하단에 페이지 번호 표시
            function displayPagination(pagination) {
               var paginationEl = document.getElementById('pagination'), 
                  fragment = document.createDocumentFragment(), 
                  i

               // 기존에 추가된 페이지 번호 삭제
               while (paginationEl.hasChildNodes()) {
                  paginationEl.removeChild(paginationEl.lastChild)
               }

               for (i = 1; i <= pagination.last; i++) {
                  var el = document.createElement('a')
                  el.href = '#'
                  el.innerHTML = i

                  if (i === pagination.current) {
                     el.className = 'on'
                  } else {
                     el.onclick = (function(i) {
                        return function() {
                           pagination.gotoPage(i)
                        }
                     })(i)
                  }

                  fragment.appendChild(el)
               }
               paginationEl.appendChild(fragment)
            }

            // 지도 위에 표시되고 있는 마커를 모두 제거합니다
            function removeMarker() {
               for (var i = 0; i < markers.length; i++) {
                  markers[i].setMap(null);
               }
               markers = [];
            }

            // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
            // 인포윈도우에 장소명을 표시합니다
            function displayInfowindow(marker, title) {
               var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

               infowindow.setContent(content);
               infowindow.open(map, marker);
            }

            // 검색결과 목록의 자식 Element를 제거하는 함수입니다
            function removeAllChildNods(el) {
               while (el.hasChildNodes()) {
                  el.removeChild(el.lastChild);
               }
            }

            //첫번째 띄울 좌표

            var first_positions = [

               {
                  latlng : new kakao.maps.LatLng(35.839548949033336, 128.60512942482416)
               }

            ];

            // 첫번째 마커 생성

            for (var i = 0; i < first_positions.length; i++) {

               // 마커를 생성합니다

               var marker = new kakao.maps.Marker({

                  map : map, // 마커를 표시할 지도

                  position : first_positions[i].latlng

               });


               kakao.maps.event.addListener(marker, 'click', marker_click(map, marker, infowindow));
            }

            function marker_click(map, marker, infowindow) {
                var isDoubleClick = false;
                var clickTimeout;

                return function() {
                    if (!isDoubleClick) {
                        isDoubleClick = true;
                        clickTimeout = setTimeout(function() {
                            // Single click action
                            infowindow.open(map, marker);
                            setTimeout(function() {
                                isDoubleClick = false;
                            }, 500);
                        }, 300);
                    } else {
                        // Double click action
                        clearTimeout(clickTimeout);
                        removeMarkerAndLine(marker);
                        isDoubleClick = false;
                    }
                };
            }
            // 마커와 연결된 선을 삭제하는 함수
            function removeMarkerAndLine(marker) {
                // Remove the marker from the markers array
                var index = markers.indexOf(marker);
                if (index !== -1) {
                    markers.splice(index, 1);
                }

                // Remove the marker from the map
                marker.setMap(null);

                // Remove the line if it exists
                if (markers.length > 0) {
                    var lastMarker = markers[markers.length - 1];
                    var linePath = [lastMarker.getPosition(), marker.getPosition()];
                    removePolyline();
                }
            }

            // 선을 삭제하는 함수
            function removePolyline() {
                if (polyline) {
                    polyline.setMap(null);
                    polyline = null;
                }
            }

            // 지도에 마커를 표시합니다
            marker.setMap(map);