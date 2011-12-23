## 자게이를 위해 재미로 만든 Slrclub 자유게시판 이미지 수집기입니다 

Eclipse Package로 구성되어있습니다. 

Egit Plugin으로 추가하시면 돼요 ^^ 

### 사용기술 

* Maven (Packaging)
* Html Cleaner (Html 파싱) 
* jdom (파싱된 html에서 Xpath Quering)


### 빌드방법 

선행 요구사항 : git client Maven 2.2+ , Java 1.6이상이 설치되어있어야 합니다. 

	$>git clone git@github.com:dsdstudio/slrclubgrabber.git
	$>cd slrclubgrabber
	slrclubgrabber$> mvn clean package


### 실행방법 

빌드절차를 거친후 아래 명령으로 수행할수 있습니다. 

	slrclubgrabber$>java -jar target/slrclubgrabber-0.1.jar

	Usage: java -jar slrclubgrabber.jar <id> <password> <savefilepath> <keyword>    
	예제) java -jar slrclubgrabber.jar userid password c:/jogong jogong     

설정한 키워드로 검색된 게시물의 이미지를 설정한 디렉토리에다 차곡차곡 쌓아줍니다 :) 

Enjoy! 
