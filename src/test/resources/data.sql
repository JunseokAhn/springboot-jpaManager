INSERT INTO COMPANY (SELECT '1', '도시1', '거리1', '우편번호1', '회사1');


INSERT INTO TEAM (SELECT '1', '팀1', '업무1', '1'
                  UNION ALL
                  SELECT '2', '팀1', '업무1', '1'
                  UNION ALL
                  SELECT '3', '팀1', '업무1', '1');

INSERT INTO MEMBER (SELECT 'Member', '1', '도시1', '거리1', '우편번호1', '유저1-1', '신입', '3000000', 'WAIT', NULL, '1'
                    UNION ALL
                    SELECT 'Member', '2', '도시1', '거리1', '우편번호1', '유저1-2', '신입', '3000000', 'WAIT', NULL, '1'
                    UNION ALL
                    SELECT 'Member', '3', '도시1', '거리1', '우편번호1', '유저1-3', '신입', '3000000', 'WAIT', NULL, '1'
                    UNION ALL
                    SELECT 'Member', '4', '도시1', '거리1', '우편번호1', '유저2-1', '신입', '3000000', 'WAIT', NULL, '2'
                    UNION ALL
                    SELECT 'Member', '5', '도시1', '거리1', '우편번호1', '유저2-2', '신입', '3000000', 'WAIT', NULL, '2'
                    UNION ALL
                    SELECT 'Member', '6', '도시1', '거리1', '우편번호1', '유저2-3', '신입', '3000000', 'WAIT', NULL, '2'
                    UNION ALL
                    SELECT 'Member', '7', '도시1', '거리1', '우편번호1', '유저3-1', '신입', '3000000', 'WAIT', NULL, '3'
                    UNION ALL
                    SELECT 'Member', '8', '도시1', '거리1', '우편번호1', '유저3-2', '신입', '3000000', 'WAIT', NULL, '3'
                    UNION ALL
                    SELECT 'Member', '9', '도시1', '거리1', '우편번호1', '유저3-3', '신입', '3000000', 'WAIT', NULL, '3');