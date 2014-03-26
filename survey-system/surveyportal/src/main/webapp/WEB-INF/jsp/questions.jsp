<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="pageSize" value="10"/>
<c:set var="totalQuestion" value="${fn:length(survey.questions)}"/>
<c:set var="shortText" value="${SHORT_TEXT}" />
<c:set var="longText" value="${LONG_TEXT}" />
<c:set var="date" value="${DATE}" />
<c:set var="dateRange" value="${DATE_RANGE}" />
<c:set var="money" value="${MONEY}" />
<c:set var="moneyRange" value="${MONEY_RANGE}" />
<c:set var="number" value="${INTEGER}" />
<c:set var="numberRange" value="${INTEGER_RANGE}" />
<c:set var="list" value="${LOOKUP_LIST}" />
<c:set var="document" value="${DOCUMENT_UPLOAD}" />
<c:set var="decimal" value="${DECIMAL}" />
<c:set var="decimalRange" value="${DECIMAL_RANGE}"/>

<div class="panel panel-primary">
	<div class="panel-heading">My Pending Surveys /<b> ${survey.surveyName}</b></div>
	<div class="panel-body">
		
		<div id="survey-content-info" style="padding-bottom: 15px;"> </div>
	
		<form id="submitSurveyAnswer" method="post">
			<div id="questionlist" style="font-size: 12px">
			
				<input type="hidden" id="currrentPage" name="currrentPage" value="${currrentPage}"/>
				<input type="hidden" id="surveyId" name="surveyId" value="${survey.surveyId}"/>
			
				<c:forEach items="${survey.questions}" var="question" varStatus="varquestion">
					
					<div id="question-wrapper-${varquestion.index}" class="question-wrapper">
						<div id="question">
							<b>${varquestion.index+1}. ${question.questionDescription}</b>
							<c:if test="${question.required eq true}"><a style="color: red;">*</a></c:if>
							<c:if test="${question.questionContainsDocument eq true}"><br><a class="mouse" href="question/${question.questionId}/document?_=${now}">Download Document</a></c:if>
						</div>
						<div id="answer" style="padding-top: 5px">
							<input type="hidden"name="answers[${varquestion.index}].questionId" value="${question.questionId}"/>
							<input type="hidden"name="answers[${varquestion.index}].answerType" value="${question.answerType}"/>
							<c:if test="${question.answer.answerId ne null}">
								<input type="hidden"name="answers[${varquestion.index}].answerId" value="${question.answer.answerId}"/>
							</c:if>
							<c:if test="${question.answer.answerEndId ne null}">
								<input type="hidden"name="answers[${varquestion.index}].answerEndId" value="${question.answer.answerEndId}"/>
							</c:if>
							<c:if test="${question.answer.answerStartId ne null}">
								<input type="hidden"name="answers[${varquestion.index}].answerStartId" value="${question.answer.answerStartId}"/>
							</c:if>
							<c:choose>
								<c:when test="${question.answerType eq shortText}">
									<span style="display: inline-block;">
										<input name="answers[${varquestion.index}].responseShort" class="smalldevice-element margin-top7" type="text" value="${fn:escapeXml(question.answer.responseShort)}"
										data-validate="${shortText}" alt="${varquestion.index+1}" <c:if test="${question.required}">required</c:if>/>&nbsp;
										
									</span>
								</c:when>
								
								<c:when test="${question.answerType eq longText}">
									<textarea data-validate="${longText}" name="answers[${varquestion.index}].responseLong"  rows="5" cols="25" class="smalldevice-element margin-top7" alt="${varquestion.index+1}" <c:if test="${question.required}">required</c:if>>${fn:escapeXml(question.answer.responseLong)}</textarea>
								</c:when>
								
								<c:when test="${question.answerType eq date}">
									
									<input name="answers[${varquestion.index}].responseDate" type="text" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${question.answer.responseDate}"/>" class="datepicker smalldevice-element margin-top7 datepickerimage" readonly="readonly"
									dateClass='DATE' alt="${varquestion.index+1}" alt="${varquestion.index+1}" <c:if test="${question.required}">required</c:if>/>
									
								</c:when>
								<c:when test="${question.answerType eq dateRange}">
									<div class="${dateRange}" id="${varquestion.index+1}" alt="${varquestion.index+1}">
										<input name="answers[${varquestion.index}].responseDateStart" type="text" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${question.answer.responseDateStart}"/>" class="datepicker smalldevice-element margin-top7 datepickerimage" readonly="readonly" 
										 question-range="Start" style="background-color: #eee" <c:if test="${question.required}"> required="required" </c:if>/>
										to 
										<input name="answers[${varquestion.index}].responseDateEnd" type="text" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${question.answer.responseDateEnd}"/>" class="datepicker smalldevice-element margin-top7 datepickerimage"  readonly="readonly"
										question-range="End"  style="background-color: #eee" <c:if test="${question.required}"> required="required" </c:if>/>
									</div>
								</c:when>
								
								<c:when test="${question.answerType eq number}">
									<input name="answers[${varquestion.index}].responseInteger" type="text" value="${question.answer.responseInteger}"
										data-validate="${number}"	class="smalldevice-element margin-top7"  alt="${varquestion.index+1}" <c:if test="${question.required}"> required="required" </c:if>/>
								</c:when>
								<c:when test="${question.answerType eq numberRange}">
									<div class="${numberRange}" id="${varquestion.index+1}" alt="${varquestion.index+1}">
										<input name="answers[${varquestion.index}].responseIntegerStart" type="text" value="${question.answer.responseIntegerStart}"
										class="Start margin-top7 smalldevice-element" <c:if test="${question.required}"> required="required" </c:if>/>
										to 
										<input name="answers[${varquestion.index}].responseIntegerEnd" type="text" value="${question.answer.responseIntegerEnd}"
										class="End margin-top7 smalldevice-element" <c:if test="${question.required}"> required="required" </c:if>/>
									</div>
								</c:when>
								
								<c:when test="${question.answerType eq decimal}">
									<input name="answers[${varquestion.index}].responseDecimal" type="text" value="${question.answer.responseDecimal}"
									data-validate="${decimal}"	class="smalldevice-element margin-top7"  alt="${varquestion.index+1}" <c:if test="${question.required}"> required="required" </c:if>/>
								</c:when>
								<c:when test="${question.answerType eq decimalRange}">
									<div class="${decimalRange}" id="${varquestion.index+1}" alt="${varquestion.index+1}">
										<input name="answers[${varquestion.index}].responseDecimalStart" type="text" value="${question.answer.responseDecimalStart}"
										class="Start smalldevice-element margin-top7" <c:if test="${question.required}"> required="required" </c:if>/>
										to 
										<input name="answers[${varquestion.index}].responseDecimalEnd" type="text" value="${question.answer.responseDecimalEnd}"
										class="End smalldevice-element margin-top7" <c:if test="${question.required}"> required="required" </c:if>/>
									</div>
								</c:when>
								
								<c:when test="${question.answerType eq money}">
									$<input name="answers[${varquestion.index}].responseMoney" type="text" value="${question.answer.responseMoney}"
										data-validate="${money}"	class="smalldevice-element margin-top7" alt="${varquestion.index+1}" <c:if test="${question.required}"> required="required" </c:if>/>
								</c:when>
								<c:when test="${question.answerType eq moneyRange}">
									<div class="${moneyRange}" id="${varquestion.index+1}" alt="${varquestion.index+1}">
										<div>
											<div class="inline-block">
												$<input name="answers[${varquestion.index}].responseMoneyStart" type="text" value="${question.answer.responseMoneyStart}"
												class="Start smalldevice-element margin-top7" <c:if test="${question.required}"> required="required" </c:if>/>
											</div>
											<div class="inline-block">to</div> 
											<div class="inline-block">
												$<input name="answers[${varquestion.index}].responseMoneyEnd" type="text" value="${question.answer.responseMoneyEnd}"
												class="End smalldevice-element margin-top7"   <c:if test="${question.required}"> required="required" </c:if>/>
											</div>
										</div>
									</div>
								</c:when>
								
								<c:when test="${question.answerType eq document}">
									<a class="wait button uploadDocument" id="${question.questionId}" type="button">Choose Document</a>
									<input documentName="${question.questionId}" name="answers[${varquestion.index}].responseDocId" type="hidden" 
									value="${question.answer.responseDocId}"
									class="${document}" alt="${varquestion.index+1}" <c:if test="${question.required}"> required="required" </c:if>/>
										<span style="padding: 4px 0px 4px 4px; border : grey 1px solid; border-radius: 4px; <c:if test="${empty  question.answer.responseDocId}">display:none</c:if>">
											<a documentName="${question.questionId}" href="pullSurveyDocument/${question.answer.responseDocId}"><b>${question.answer.responseDocName}</b></a>
											<span documentName="${question.questionId}" class="remove" style="display: inline-block; background-image: url('<c:url value="/static/css/images/ui-icons_222222_256x240.png"/>'); height: 16px; width: 16px; background-position: -80px -128px;"></span>
										</span>
								</c:when>
								
								<c:when test="${question.answerType eq list}">
									<select class="selectdefault smalldevice-element-select margin-top7 ${list}" name="answers[${varquestion.index}].responseLookup" alt="${varquestion.index+1}" <c:if test="${question.required}"> required </c:if> >
										<option value="">Select Option</option>
										<c:forEach items="${question.questionResponses}" var="expectedAnswer">
											<c:choose>
				   									 <c:when test="${question.answer.responseLookup eq expectedAnswer.expectedResponseText}">
														<option value="${expectedAnswer.expectedResponseText}" selected="selected">${expectedAnswer.expectedResponseText}</option>
													</c:when>
												    <c:otherwise> 
												        <option value="${expectedAnswer.expectedResponseText}">${expectedAnswer.expectedResponseText}</option>
												     </c:otherwise> 
											</c:choose>
										</c:forEach>
									</select>
								</c:when>
							</c:choose>
						</div>
						<!-- <div>&nbsp;</div> -->
					</div>
					
				</c:forEach>		
				
				<!-- Actions Buttons  -->
				<span><spring:message code="label.question"/> <span id="lowerLimit">1</span> - <span id="upperLimit">10</span> <spring:message code="label.of"/> ${totalQuestion}</span>
				
				<ul class="pager">
				  <li id="back" class="mouse" ><a><spring:message code="label.back"/></a></li>
				  <li id="next" class="mouse"><a><spring:message code="label.next"/></a></li>
				  <li id="surveySaveLater" class="mouse"><a><spring:message code="label.saveForLater"/></a></li>
				  <li id="surveySubmit" class="mouse"><a><spring:message code="label.submit"/></a></li>
				</ul>
				<!-- Actions Buttons  -->
				
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" src="static/js/surveyvalidation.js"></script>
<script type="text/javascript" src="static/js/questionsJs.js"></script>

<script type="text/javascript">
$(function(){
	  var messageBundle = {
			  requiredAnswer:"<spring:message code='message.requiredAnswer'/>",
				notMoreThan2000char:"<spring:message code='message.notMoreThan2000char'/>",
				notMoreThan255char:"<spring:message code='message.notMoreThan255char'/>",
				selectAnswer:"<spring:message code='message.selectAnswer'/>",
				provideRemoveValue:"<spring:message code='message.provideRemoveValue'/>",
				invalidAmount:"<spring:message code='message.invalidAmount'/>",
				toGreaterThanFrom:"<spring:message code='message.toGreaterThanFrom'/>",
				invalidInteger:"<spring:message code='message.invalidInteger'/>",
				invalidDecimal:"<spring:message code='message.invalidDecimal'/>",
				requiredOldPassword:"<spring:message code='message.requiredoldpassword'/>",
			 	requiredNewPassword:"<spring:message code='message.requirednewpassword'/>",
				requiredConfirmPassword:"<spring:message code='message.requiredconfirmpassword'/>",
				saveSuccessfully:"<spring:message code='message.saveSuccessfully'/>",
				formNotChanged:"<spring:message code='message.formNotChanged'/>"
	 	 };
	  var varCurrentPage=${currrentPage};
	  var varPageSize=${pageSize};
	  var totalQuestion=${totalQuestion};
	  questionsJs(jQuery,messageBundle,varCurrentPage,varPageSize,totalQuestion);
});
</script>
