<div style="width: 30%; bottom: 0; right: 0;  position: fixed; z-index:10">
		<div id="chat" style="display: block">
			<button type="button" class="btb btn-dark btn-lg btn-block" onclick="openChat()">Chat</button>
		</div>
		<div id="chatbox" style="display:none; border:solid; padding: 2%">
			<form name="chatform" class="form-container" onsubmit="return false;">
				<div>
			    	<h3 style="float:left; text-align: left">Chat</h3>
			    	<button onclick="closeChat()" style="float:right; text-align: right; border:none; background: white" id="close">
			    		<i class="fas fa-times"></i>
			    	</button>
		    	</div>
		    	<textarea placeholder="Hello!" name="chatArea" rows="7" style="width: 100%" id="chatArea" readonly><%
		    		String chatHistory=(String)session.getAttribute("chat");
			    	if(chatHistory!=null){
			    		out.print(chatHistory);
			    	}
		    	%></textarea>
				<div class="btn-group" style="width: 90%">
					<select name="userInput" id="userInput" class="btn btn-secondary btn-sm dropdown-toggle" required onChange="extraInputs()">	
						<option value="" disabled selected>Ask me a question!</option>
						<option value="When is my next appointment?">When is my next appointment?</option>
					<%					
						String role=(String)session.getAttribute("role");
						if(role.equals("Doctor")){
							out.println("<option value=\"Who is my next appointment?\">Who is my next appointment?</option>");
							out.println("<option value=\"How many appointments do I have left today?\">How many appointments do I have left today?</option>");
							out.println("<option value=\"What is my availability for: \">What is my availability for:</option>");
						}							
					%>
					</select>
				</div>
				
				<button type="button" style="float: right; text-align: right; padding: 2%; border:none; background: white" onclick="updateChat()">
		   			<i class="fas fa-paper-plane"></i>
		   		</button>
		   		
		   		<input type="date" id="availabilityInput" style="display: none">	
			</form>
		</div>
</div>
		<script>		
			$(document).ready(function(){
				document.getElementById("availabilityInput").valueAsDate=new Date();	
			});
		
			function openChat() {
				document.getElementById("chatbox").style.display = "block";
				document.getElementById("chat").style.display = "none";
			}
			
			function closeChat() {
				document.getElementById("chatbox").style.display = "none";
				document.getElementById("chat").style.display = "block";
			}
			
			var request;
			
			function updateChat(){
				if(document.getElementById("userInput").value==""){
					return;
				}
				var chatHistory=document.getElementById("chatArea").value;
				var question=document.getElementById("userInput").value;
				var newChat="Me: "+question;
				if(question.includes("availability")){
					newChat+=document.getElementById("availabilityInput").value;
				}
				chatHistory+=newChat;
				document.getElementById("chatArea").value=chatHistory+"\n";	
								
				if (window.XMLHttpRequest) {
			        request=new XMLHttpRequest();
			    } else if (window.ActiveXObject) {
			        request= new ActiveXObject("Microsoft.XMLHTTP");
			    }
				chatHistory = chatHistory.replace(new RegExp('\n', 'g'), '_');
				var path = "chatbox?chatArea="+chatHistory+"&userInput="+newChat;
		        request.open("GET", path); //sends request asynchronously
		        request.onreadystatechange = showAnswer;
		        request.send();
			}
			
			function showAnswer(){				
				if(request.readyState==4){  
					if(request.status==200){						
						var answer=request.responseText;
						var chatHistory=document.getElementById("chatArea");
						var history=chatHistory.value;
						history+=answer;
						chatHistory.value=history;
						document.getElementById("userInput").value="";
						document.getElementById("chatArea").scrollTop=document.getElementById("chatArea").scrollHeight;
						document.getElementById("availabilityInput").style.display="none";
					}
				}					
			}
			
			function extraInputs(){
				var select = document.getElementById("userInput");
				var value = select.options[select.selectedIndex].value;
				if(value=="What is my availability for: "){
					document.getElementById("availabilityInput").style.display="block";
				}
				else{
					document.getElementById("availabilityInput").style.display = "none";
				}
			}
		</script>