package vesel.newsservice.mapper;

import vesel.newsservice.dto.response.AnsichtenResponse;
import vesel.newsservice.entity.Ansichten;

import java.util.List;

public class AnsichtenMapper {
    public static AnsichtenResponse getAnsichtenResponseByArticle(List<Ansichten> ansichtens) {
        return new AnsichtenResponse(ansichtens.size());
    }

}
