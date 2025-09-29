<%@ page contentType="text/html; charset=UTF-8" language="java" %>
	<!-- Footer -->
	<footer class="bg-dark text-light text-center py-3 mt-auto">
	  <div class="container">
	    <small>&copy; 2025 Spring Final Project. All rights reserved.</small>
	  </div>
	</footer>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script>
  	let expire = '${expire}';
  	if(expire != ''){
  		alert(expire);
  		location.href="${path}/user/logout";
  	}
  </script>
</body>
</html>