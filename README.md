## 자게이를 위해 재미로 만든 Slrclub 자유게시판 이미지 수집기입니다 


### 사용기술 

* Maven (Packaging)
* Html Cleaner (Html 파싱) 
* jdom (파싱된 html에서 Xpath Quering)


### 빌드방법 

Maven 2.2+ , Java 1.6이상이 설치되어있어야 합니다. 

	slrclubgrabber$> mvn clean package

빌드후 outputfile이 생기는데 이것으로 실행합니다. 


