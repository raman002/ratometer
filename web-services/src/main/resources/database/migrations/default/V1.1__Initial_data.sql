insert into role_master(role_master_id, role_name)
values (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'SUPER_ADMIN');


insert into users (users_id, username, email, full_name, password)
values
-- password : steve
(1, 'steve.adams', 'steve.adams@ratometer.com', 'Steve Adams',
 '$2y$10$f7/KYCl04dGslZPgO0Lpj.G4OXgql5DS76aQmrepkfUtYPBmmvQci'),
-- password : mark
(2, 'mark.paul', 'mark.paul@ratometer.com', 'Mark Paul',
 '$2y$10$26rO7JRv41YNYmSuo425VuOtKYOvHFv7INT/8IFY37ZJt3nyenOAS'),
-- password : vikas
(3, 'vikas.singh', 'vikas.singh@ratometer.com', 'Vikas Singh',
 '$2y$10$Uahg.IPKpu/fiOR4jBh7Cu8i18SoLuePuBubG0MClnslAcSy7IWc2'),
-- password : rohit
(4, 'rohit.thakur', 'rohit.thakur@ratometer.com', 'Rohit Thakur',
 '$2y$10$TcZ352MixlHlLxklQSC.QeHNmHYaFGAGrNxwljz9v3DTsE1iptE/C');


-- Assigning Roles to the users
insert into roles(users_id, role_master_id)
values (1, 3), -- Assigning SUPER ADMIN role to Steve Adams.
       (2, 1), -- Assigning USER role to Mark Paul.
       (3, 1), -- Assigning USER role to Vikas Singh.
       (4, 1);
-- Assigning USER role to Rohit Thakur.


-- Creating teams
insert into teams_master(teams_master_id, name, uuid)
values (1, 'TEAM A', '8d8c9c02-5bef-4c48-993d-face3fb7d365'),
       (2, 'TEAM B', '4e8038b1-40f2-4796-81b5-7969d2c676f3'),
       (3, 'TEAM C', '394be8e9-ab86-40b7-8f78-eeee688ee233');



-- Assigning Teams to the users
insert into teams(teams_id, users_id, teams_master_id, teams_master_uid)
values (1, 2, 1, '8d8c9c02-5bef-4c48-993d-face3fb7d365'), -- Assigning mark to TEAM A
       (2, 3, 2, '4e8038b1-40f2-4796-81b5-7969d2c676f3'), -- Assigning vikas to TEAM B
       (3, 4, 3, '394be8e9-ab86-40b7-8f78-eeee688ee233');
-- Assigning rohit to TEAM C

-- Categories
insert into categories_master(categories_master_id, name, type, uuid)
values (1, 'Strategy and businesspartnership', 'CATEGORY', '2c51d73b-ac57-47e9-9a67-05cfb46d5e8b'),
       (2, 'Cont Improvement Culture', 'CATEGORY', 'a28c47c4-f075-4456-887d-9c69c2df0eb9'),
       (3, 'Requirement and Prioritization', 'CATEGORY', '92c460c3-faa2-4356-be3c-c414ba219955'),
       (4, 'Effective Agile Process', 'CATEGORY', 'b8326ba4-41bf-487f-8652-2336c8637538'),
       (5, 'Book of Work Management', 'CATEGORY', 'd4573043-dfbc-4d88-a4c7-79d3ec11e694'),
       (6, 'Agile Organization', 'CATEGORY', 'a9d990c0-25f6-43da-a00d-b70059e584dd'),
       (7, 'Source Control', 'CATEGORY', '171a3a92-20a5-49e4-895f-9512b039fdf2'),
       (8, 'Standardization and BuildChain', 'CATEGORY', '8b3986e8-e0af-47ad-9e45-13b82ff716a6'),
       (9, 'Artefact Management', 'CATEGORY', 'c3842276-3156-42fb-9dd2-a5aedb3c3249'),
       (10, 'Automation', 'CATEGORY', '3d239b4d-77d6-499d-96c3-82dff81485d1'),
       (11, 'Quality in Design and Coding', 'CATEGORY', '317573d8-0b34-4c68-ac70-e0458a5a24e3'),
       (12, 'Continuous Integration', 'CATEGORY', '601e1c84-23a7-4269-b5d5-e47abb79223e');
-- Sub categories
insert into categories_master(categories_master_id, parent_category_id, name, type, uuid)
values (13, 1, 'Business and Technical Strategy', 'SUB_CATEGORY', 'cdf9632c-2982-421f-8761-ad3eb74039ca'),
       (14, 1, 'Business-IT Partnership', 'SUB_CATEGORY', 'd662a98f-58dd-4071-bab9-8b3c5dcfbf86'),
       (15, 2, 'Continuous Improvement', 'SUB_CATEGORY', '2f1f6d33-c60c-49fc-99a5-7b9aaabd55bc'),
       (16, 3, 'Requirements Quality / Defination of Ready', 'SUB_CATEGORY', '247d0aea-a735-4b26-bd7b-dc322301acb8'),
       (17, 3, 'Prioritization & Regular Stakeholder Feedback', 'SUB_CATEGORY', '0332ec06-86af-4c02-a1fe-2eca87e7989c'),
       (18, 4, 'Planning And Estimation', 'SUB_CATEGORY', '00ed1661-93e7-4cfc-9459-c0ed9ef295e4'),
       (19, 4, 'Potentially Shipable Product', 'SUB_CATEGORY', '7fec60fd-861d-4364-8d39-f8a1f5349995'),
       (20, 4, 'Definition of Done', 'SUB_CATEGORY', '8c22919f-db33-4d52-b505-edc5d86a1a43'),
       (21, 5, 'Backlog Management & Progress Transparency', 'SUB_CATEGORY', '4db571d8-c058-4e46-8ad3-35a8d4777817'),
       (22, 6, 'Team Design and Roles', 'SUB_CATEGORY', '222a8000-1296-49c9-941b-78ef11eeebec'),
       (23, 7, 'Source Control/ Collective Code Ownership', 'SUB_CATEGORY', '773cc14e-c48f-4b99-9b85-223410cfeca5'),
       (24, 8, 'Build Infrastructure', 'SUB_CATEGORY', '81dfac83-fc97-47e0-b730-2b6266e122f0'),
       (25, 9, 'Artefact Management', 'SUB_CATEGORY', '15b876e6-99bf-459c-baad-d93ff0c8fbe1'),
       (26, 10, 'Test Automation', 'SUB_CATEGORY', '82f532e6-7297-4a19-96f8-c802b6e6f6b3'),
       (27, 11, 'Technical Debt management and Code Quality', 'SUB_CATEGORY', '03199d0d-c681-4e6f-803b-75d0bd656305'),
       (28, 12, 'Continuous Integration', 'SUB_CATEGORY', 'd84475d1-362a-4e26-b5dc-1883d115c09b');

-- Options

insert into categories_master(categories_master_id, parent_category_id, name, type, option_order_id, uuid)
values (29, 13, 'Limited Understanding of Business Goal and Technicl Strategy', 'OPTION', 1, 'c67fd333-505c-418d-821a-0e753b22722a'),
       (30, 13, 'Business Strategy and Technical Strategy Partly Exist', 'OPTION', 2, '7b8a107f-4bc1-421f-ba71-c37f96612b42'),
       (31, 13, 'Business and Technical Strategist and working collaberatively', 'OPTION', 3 , '3638c06a-c2f2-477d-8e8e-1d56e117beea'),
       (32, 13, 'The Entire team has good Understanding of Business Goals', 'OPTION', 4, '7cec4c48-abf7-41cc-bf12-98aed171a9d9'),
       (33, 13, 'The Team partner with the business to suggest improvmentsbased on technical/Business trends',
        'OPTION', 5, '5a3557bb-3ab2-4b32-8145-6b98f76d1f20'),

       (34, 14, 'Business reps not available on regular basis', 'OPTION', 1, '336885ae-c042-4e04-a252-e9042798f984'),
       (35, 14, 'Team have to request access to business representatives, BA''s or Proxies', 'OPTION', 2, '28f5fbed-4d8f-4df6-a608-71233362be2b'),
       (36, 14, 'Access to business is ensured within a day', 'OPTION', 3 , 'cbe395f6-b910-4ace-96f8-7c0424916376'),
       (37, 14, 'Business and IT co-located', 'OPTION', 4, '20202c0a-8843-4d78-ab09-bc12982af998'),
       (38, 14, 'Proven ROI/ Delivered Business value exist', 'OPTION', 5, '32963442-4ada-4514-a0e7-d9555904dc61'),

       (39, 15, 'Team is not aware about retrospectives', 'OPTION', 1, '4c7e03d7-6153-4f76-8fb7-e473d861201d'),
       (40, 15, 'Retrospectives ar not held or held infrequently and /or not follow-up', 'OPTION', 2, '854d33f0-854f-48f0-bf69-db00635e6d6e'),
       (41, 15, 'Retrospectives are held at the end of each sprint', 'OPTION', 3 , '628656b9-ba10-4160-8ebd-a23aefeeaa57'),
       (42, 15, 'Continuous Improvement cuture is in place', 'OPTION', 4, '3c88623f-6c07-480a-8548-11c06907b22a'),
       (43, 15, 'Teams strives to identify new oppurtunities for improvement or business growth', 'OPTION', 5, '69299c47-20de-4fb6-b129-b42c67caa306'),

       (44, 16, 'Team often starts development  with unclear requirements', 'OPTION', 1, 'e27ff566-0a44-4d02-b247-84e988a69797'),
       (45, 16, 'Clear quality expectations for requirements( eg definition of ready) is not agreed', 'OPTION', 2, '5ba1aaba-5244-4cc4-b3fe-7d4b7170117e'),
       (46, 16,
        'Quality expectations for a requirement are defined(eg definition of ready)  and lived with the business',
        'OPTION', 3 , 'c8fe821c-62f3-445b-8a56-8fd00cf52485'),
       (47, 16, 'Operating + Business well integrated with mature product owner role', 'OPTION', 4, '35dec940-caa7-4dc8-a160-454810cfd3e3'),
       (48, 16, 'Team and Business establised and optimizes a shared method such as SBE(Specification by example)',
        'OPTION', 5, 'b0587901-8bdd-4ba8-b50b-4735721523c0'),

       (49, 17, 'No Review feedback cycle with business Unclear prioritization', 'OPTION', 1, 'c737eb83-148d-4745-9ee3-b5a4cf0b6508'),
       (50, 17, 'Irregular feedback meetings Iteration scope unstable', 'OPTION', 2, 'c6cf1ac6-1a8e-4cb6-a3de-9f7bd7fcb71a'),
       (51, 17, 'Review meeting held regularly', 'OPTION', 3, '28427028-ac91-43fd-956f-07711eff2d9d' ),
       (52, 17, 'Iteration scope>80% stable', 'OPTION', 4, '98395cce-6aa3-4800-8a1d-6215fec75c41'),
       (53, 17, 'Regular review meetings always held with business', 'OPTION', 5, 'd8243fce-f06e-45a6-8c77-1dac158a53ae'),

       (54, 18, 'Planning is done by individual for large piece of work', 'OPTION', 1, '0d3e6e4e-c637-4bd1-bff4-5ad81d404354'),
       (55, 18, 'Team offen overcommit work. Team velocity not well understood', 'OPTION', 2, 'b16ba528-0c0b-49bd-a6fc-3456c4d20297'),
       (56, 18, 'Team plan and estimates reliably and collaboratively', 'OPTION', 3, '07418852-d7d0-46ed-a9a7-5a55be5f8683' ),
       (57, 18, 'Estimation is rapid and consistently institutionalised with the team', 'OPTION', 4, '03398876-4af8-4c02-a518-9a32c49a28c4'),
       (58, 18, 'All work items are broken down by the team into similar sizes', 'OPTION', 5, '422625ae-f925-49ae-88a1-d2af33bac4bd'),

       (59, 19, 'There is one big delivery at the end of the development and test window', 'OPTION', 1, 'b4585526-f847-4c62-8e2d-bdf961f08920'),
       (60, 19, 'Team rarely delivers Potentially Shipable Product per iteration', 'OPTION', 2, '1fbbad0b-93a6-45aa-b5b6-5d6fac07cb05'),
       (61, 19, 'The Team produces a Potentially Shipable Product after around 50 % of the iterations', 'OPTION', 3, '4d21d411-2d57-4d6c-a99e-6345edc2f1ba' ),
       (62, 19, 'Team can deliver Potentially Shipable Product after every iteration', 'OPTION', 4, 'b2e2153e-af89-4df4-a742-541579f8e478'),
       (63, 19, 'The team delivers a Potentially Shipable Product multiple times per iteration', 'OPTION', 5, '1afe638b-f64a-4fa1-9b60-610c66c6a7f2'),

       (64, 20, 'Team does not have a shared understanding of definition of done (DOD)', 'OPTION', 1, '1197fdba-b498-4de7-b50e-48790b241d9b'),
       (65, 20, 'Team has an understanding of definition of done (DOD) but it is not followed', 'OPTION', 2, '6a2efb44-9196-4df0-89fc-c19a733be76f'),
       (66, 20, 'The team has clear and documented set of DOD rules are follow them', 'OPTION', 3 , '529d9623-a131-44ac-a107-c39d882618c5'),
       (67, 20, 'KPIs are in place to ensure the DOD is met', 'OPTION', 4, 'd028e65e-fcf3-47e9-9031-fa51c29abf10'),
       (68, 20, 'DOD is naturaly embedded into the team process', 'OPTION', 5, '19a7f72e-2f00-4249-ad29-e1d5fac55903'),


       (69, 21, 'Lack of transparency on requirements and current state(eg Requirement held inlarge documents)',
        'OPTION', 1, '2b5f0d0a-71e6-4732-a0d2-460eb364bda8'),
       (70, 21, 'Unclear what individual resources are working on (eg bacause several backlogs of work exist)',
        'OPTION', 2, '2ab1fd30-f49d-499f-bb42-b56f2998ea8c'),
       (71, 21,
        'There is one repository for requirements are tasks. Tehe backlog are work progress is visible to everybody',
        'OPTION', 3 , 'd924236d-4700-4f2f-8e2a-cd8bc7536907'),
       (72, 21, 'Operation changes can be traced to budget and code', 'OPTION', 4, '5b4d9aa1-da3c-4476-aaf9-5153f04dc8b3'),
       (73, 21, 'Adapting - there is a small backlog of user stories', 'OPTION', 5, 'c819ab04-14d6-4ac2-a9e0-5e0c709b3ad9'),

       (74, 22, 'Members only part time commtted to team. High attrition in the team', 'OPTION', 1, '61dcd31f-c1fe-49e2-a1c8-67599a57cc16'),
       (75, 22, 'Teams commited but not structured by roles(eg seperated test/ dev teams)', 'OPTION', 2, 'a62ae13d-7512-48ea-8bad-84b6f10b44ac'),
       (76, 22, 'Teams are setup as feature/cross functional teams', 'OPTION', 3, '094aa672-ce70-4f43-8302-ebae62c197d7' ),
       (77, 22, 'Teams are colocated and have autonomy as feature teams', 'OPTION', 4, 'c540b4ed-d118-4e24-9001-222b41fd2297'),
       (78, 22, 'team is highly flexible and roles are fungible', 'OPTION', 5, '2276f738-9bb3-4104-b4fe-66fd29074679'),

       (79, 23, 'No Centralised source code management in place', 'OPTION', 1, '678f8dc6-9438-4c6f-9150-e0c2462f01ab'),
       (80, 23, 'Basec source code management practices', 'OPTION', 2, 'fb393f81-3d8b-476c-a15b-d7f6f319505c'),
       (81, 23, 'using standard git hub/changeman. Agreed branching /Review approach in place', 'OPTION', 3, '0f53cf82-dfc0-4f45-8e1e-9439dabb6559' ),
       (82, 23, 'Advance braching strategy in place. All changes are peer review', 'OPTION', 4, '37f0a1af-d60a-481e-bd50-fbe4488ae66b'),
       (83, 23, 'Team is expect in source control topics and supports other teams', 'OPTION', 5, '8eceb751-19b3-4b86-bd62-81388c004ee0'),

       (84, 24, 'Team does not have a build infrastructure shared by all developers', 'OPTION', 1, '2d97a4b3-ae96-4db8-8aca-b910e39748d5'),
       (85, 24, 'Team has a defined infrastructure to build software', 'OPTION', 2, '9cbe41dd-7625-48c1-81a5-1bb65114229b'),
       (86, 24, 'Team use one of the UBS standard build infrastructures', 'OPTION', 3 , '1cf9485d-d243-4f07-8654-9be41ddad400'),
       (87, 24, 'Team runs all the builds using the standardization build chain', 'OPTION', 4, 'b149657d-3519-4dd2-8349-a60c0ad8288e'),
       (88, 24, 'Team fully leverages the centralised build servers and acts as  SMEs', 'OPTION', 5, 'b511c08a-583a-4752-a61e-f3fdb54bad2e'),

       (89, 25, 'Team does not use central repository to store sources', 'OPTION', 1, 'f25f75a5-8b08-4921-8696-61d7d56d471b'),
       (90, 25, 'Team does use a version control system to manage sources', 'OPTION', 2, '04e8c908-66f9-4cb5-8881-43d227e577ff'),
       (91, 25, 'Team is using the shared NEXUS Repository', 'OPTION', 3, 'f3907eec-2108-46a3-981c-3e1cfe8825a3' ),
       (92, 25, 'The team applies standards for the repository structure', 'OPTION', 4, '8b4808c8-c607-4beb-90f3-bf91c384532e'),
       (110, 25, 'Team fully leverages the shared NEXUS Repository', 'OPTION', 5, 'e5edab62-0ac7-4d9b-b1ad-3426e232e5e2'),

       (93, 26, 'No or hardly any test automation in place Amount of Automation < 20%', 'OPTION', 1, '38687775-bf80-476b-b59e-25d392f6b1a6'),
       (94, 26, 'Some test automation in place Amount of Automation < 40%', 'OPTION', 2, 'd3746486-69ef-47fa-a285-b9ab50c81a5a'),
       (95, 26, 'New test are added to regression test set Amount of Automation < 60%', 'OPTION', 3, 'bc72ca93-b813-4f8b-beed-1322225028e7' ),
       (96, 26, 'Regression test set is part of Continuous Integration Amount of Automation < 80%', 'OPTION', 4, '6c8d82be-4153-4c84-a89d-fbeb7f62a5d7'),
       (97, 26, 'Due to the test automation coverage no manual test are needed', 'OPTION', 5, '51a652df-9d22-4753-bb2d-56c09e7e17aa'),

       (98, 27, 'No awareness of technical debt or the need to resolve it', 'OPTION', 1, '2a0a51ea-86d3-4ce1-b805-117615463bc4'),
       (99, 27, 'Some awareness of technical debt but no strategy exists', 'OPTION', 2, 'f8d919b9-9436-4066-be37-d9a14a7732ec'),
       (100, 27, 'Team actively tracks/ manages  its technical debt', 'OPTION', 3, '09f81c8d-829c-40b9-8ed3-87b59dc94ad6' ),
       (101, 27, 'Team capacity is put aside in every iteration to reduce  technical debt', 'OPTION', 4, '76dda645-8981-43b0-9abb-9857d606b6f8'),
       (102, 27, 'Team consistly plans and works towards their Quality targets', 'OPTION', 5, '8b8cc512-7b65-4302-afb2-fcc40a5b601b'),

       (103, 28, 'Continuous Integration is not implemented', 'OPTION', 1, 'b28ec1ac-692f-4b9c-b2b7-183dcf8fc4ef'),
       (104, 28, 'code is Continuously Integration at least weekly', 'OPTION', 2, '727ad6d7-5ec1-4734-9fb9-951723544c9d'),
       (105, 28, 'Continuous Integration occurs daily to an early test environment', 'OPTION', 3, 'ab6d2ddf-8dea-49ae-adec-7d88f6304e7e' ),
       (106, 28, 'Continuous Integration occurs daily to a system test environment', 'OPTION', 4, '61c06be5-8470-4376-b075-e288c29a71df'),
       (107, 28, 'Build breaks/bugs are fixed immedialy', 'OPTION', 5, '75a8c0e4-773f-402a-8f9e-d35e2160b510');

