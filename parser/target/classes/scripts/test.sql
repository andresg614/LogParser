SELECT rr.id as id, rr.ip_address as ip_address, countview.total as total 
FROM requester AS rr, (SELECT id_requester as id_request, count(*) as total
	FROM request AS rt 
	WHERE rt.request_date >= '2017-01-01 13:00:00'
	AND rt.request_date <= '2017-01-02 12:59:59'
	GROUP BY id_requester) AS countview
WHERE rr.id = countview.id_request
AND countview.total > 250
ORDER BY rr.ip_address;

SELECT rt.request_date, rt.request_description, rt.request_status, rt.request_user_agent
FROM request AS rt, requester AS rr  
WHERE rt.id_requester = rr.id
AND rr.ip_address = '192.168.11.231'
ORDER BY rt.request_date;