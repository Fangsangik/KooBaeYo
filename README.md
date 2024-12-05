## ProjectName : KooBaeYoo
## 🛠️ Tools :  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=github&logoColor=Green"> <img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>  <img alt="Java" src ="https://img.shields.io/badge/intellijidea-000000.svg?&style=for-the-badge&logo=intellijidea&logoColor=white"/>
## 👨‍💻 Period : 2024/12/03 ~ 2024/12/09
## 👨‍💻 ERD 
![ERD](https://github.com/user-attachments/assets/3a047717-b273-4dd4-9cda-e240abb32f12)
## 👨‍💻 API 명세서 
## 👨‍💻 기능 설명 
- User
  - CRUD (생성, 조회, 수정, 삭제)
- Menu
  - CUD (생성, 수정, 삭제)
- Store
  - CRUD (생성, 조회, 리모델링, 문 닫음)  
- Order
  - CRU (생성, 단건 조회, 다건 조회, 상태 수정)
- Review
  - CR (리뷰 생성, 가게 리뷰 조회, 리뷰 별점 조회)   
  
## 🥵 Troubleshooting
- User 
  1. 회원 로그인이 중복으로 처리되는 문제 발생
  -> 세션이 존재할 경우 다시 로그인하지 못하도록 예외처리
  2. 회원이 삭제되어도 로그인이 되는 문제 발생
  -> soft-deleted로 상태값만 변경되어 회원 정보가 그대로 남아있어 발생한 것으로 보임
  -> isdeleted 값이 true인 경우 로그인 하지 못하도록 예외 처리
  3. 회원 가입 로직 실행시 HttpMediaTypeNotAcceptableException 발생
   -> SignUpResponseDto에 @Getter를 추가하여 문제 해결
   4. git hub merge한 브랜치 revert 후 다시 merge 시 로컬에서 푸시가 안되는 문제 발생
  -> 원격으로 개인 브랜치에서 main 브랜치로 pull request를 보내서 해결

- Store
  1. 가게 단건 조회시에, 가게의 메뉴를 가져올 때 메뉴에도 가게정보가 있어서 메뉴정보만 가져오는 것이아니라, 메뉴에 포함된 가게의 모든 정보를 가져오는 문제가 발생
    문제 해결 방안이 2가지 정도있었습니다.
      1) Menu를 가져오는 것이 아니라 필요한 값만 Dto를 생성해서 가져오는 방식. . .
      2) @JsonIgnore을 사용하는 방식. . .
    @JsonIgnore을 사용해서 메뉴에 포함된 가게의 모든 정보를 가져오지 않게 처리하였습니다.

- order
  1. 주문 목록 조회할때 user, store 구분 문제
   처음에는 if문으로 userId, storeId 구분을 했지만, 2개가 모두 들어 있는 경우, null 값인 경우는 로그인할 때 이미 걸러져서 따로 구분이 필요 없어서
  -> SessionAttribute, private boolean isOwner(User user)을 이용해
   Admin,User로 구분해서 주문 목록을 조회하게 했습니다.
  3. git pull origin main 이 제대로 받아지지 않는 문제
  ->최신상태인데 계속 깃과 안에 구성이 다른 문제가 발생
  ->캐시 삭제후 정상적으로 업데이트되었습니다.
  4. 데이터 베이스 문제로 실행이 안됨
  ->설정에서 Gradle에서 다음을 사용하여 빌드 및 실행을 Gradle로 바꾸니 정상화되었습니다.
  5. OrderService작성시 주문과 연결된 메뉴, 스토어, 유저를 서비스로 연결 하려했을때, service는 dto에서 가져오니까 형변환의 오류가 있었는데
  ->Repository를 이용해서 연결하니, Entity로 가져오니 오류가 사라졌습니다.

- Menu
  1. 메뉴 생성 시, 메뉴의 한 필드(storeid) 에서 NullPointException 이 발생
  2. 각 계층에 아래와 같이 로그 값을 출력하여 파리미터 값과, 어떤 계층에서 오류가 발생되는 지 확인
    • log.info(“MenuControlelr 호출”)
    • log.info(“ 파라미터 확인 {}, {}”,   userId, storeId) …
  3. 로그 값을 통해 service 계층으로 필드 값(storeId) 값이 넘어가지 않는 것을 확인
  4. 해당 필드에 값을 정상적으로 넘겨줘 해결함.

- Review
  1. CreateReview
Review를 생성할때 Repository에서 OrderId를 Join 해서 가져왔지만, 팀원분 중 한분이 굳이 그렇게 가져올 필요 없이 orderRepo에서 id 값을 가져와서 가게 조회 할때 order.getStore 이렇게 해결
  2. findReview
최신날짜로 내림차순 처리를 한것이 아닌, id 값으로 내림차순을 진행했다.

## 👨‍💻 보안할 점 
지금 플젝은 각 기능의 기본적인 기능만 구현이 되어있습니다. 
추후 Category, ADMIN의 관리자 서비스 확대, 결제 시스탬, 로그인시 OpenAPI를 가져와서 로그인 구현등 세밀한 부분과 기능확장에 대해 도전해볼 예정입니다
또한 Session을 이용해 로그인을 처리했지만, JWT토큰을 통해 보안 기능을 활용 해 볼 예정입니다. 

## 👍 회고

- 박무근

팀원들과 계속해서 소통하고 문제를 해결할 수 있어서 좋았습니다.
도메인을 각자 담당해서 개발을 진행해서 서로 충돌날 일이 없어서 매우 편했습니다.
에러와 응답을 공통으로 처리할 수 있어서 코드의 통일성을 볼 수 있어서 좋았습니다.

- 김민주

좋은 팀원들을 만나 부족한 부분을 정말 많이 배울 수 있었습니다.
아직 로그인 세션 부분의 이해가 부족한 것 같아 추후 추가로 공부할 필요를 느꼈습니다.
2번재 git 협업을 하며 git에 대해 배운 부분이 있으며 이부분 또한 추가학습의 필요성을 느꼈습니다.

- 황상익

팀원들을 잘 만난 덕분에 편한 환경에서 편하게 코드작업을 할 수 있었고 다들 본인의 Domain을 최선을 다해주셔서 덕분에 잘 완성 할 수 있었습니다

- 이경섭

좋은 팀원들 만나서 많이 배운 것 같습니다. 특히 DDD 개발 방식을 프로젝트에 적용하여  각 기능별로 버전을 관리할 수 있어, 충돌없이 원할하게 협업을 진행할 수 있었던 것 같습니다

- 장수연

팀원들과 소통이 잘 되서 너무 좋았습니다.
특히 모르는 문제가 있을때 무작정 알려주기보다는 직접 해결 할 수 있는 시간을 주었고, 그래도 모르는 경우에만 친절하게 해당 부분을 알려줘서 좋았습니다.
