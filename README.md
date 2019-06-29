# DemoRecyclerView

Demo bao gồm các thành phần

- MainActivity.java: 
	+ Activity chính của app, chứa custom list
	+ Khi mở app, tiến hành gửi request lấy data và đổ vào list
	+ Nếu không request lỗi, load từ local data (có thể implement database local trong tương lai)

- RecyclerViewAdapter.java: 
	+ Adapter phục vụ cho việc đổ data vào list
	+ Custom layout

- DataManager.java:
	+ Chứa hàm gọi Get request để lấy data và cache dữ liệu khi đã lấy về
	+ Chạy background thread, được gọi mỗi khi app onResume()

- AppUtils.java:
	+ Chứa hàm cắt và thay thế chuỗi.
	+ Ý tưởng giải thuật: Nếu chuỗi có nhiều hơn 1 từ, cắt làm 2 dòng dựa trên trị tuyệt đối ngắn nhất của độ dài 2 dòng với nhau.
