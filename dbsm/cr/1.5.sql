select TeamName
from Teams
natural join (
    select distinct TeamId from (
        select TeamId, ContestId
        from Teams t
        natural join Sessions s
        where not exists (
            select TeamId, ContestId
            from Sessions s1
            natural join Runs r1
            where Accepted = 1
            and t.TeamId = s1.TeamId
            and s.ContestId = s1.ContestId
        )
    ) TeamByContest
) ResultTeams;