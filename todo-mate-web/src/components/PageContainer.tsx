import styled from "styled-components";
import {colors} from "../styles/colors.ts";

export default function PageContainer({ children }: { children: React.ReactNode }) {
    return <Container>{children}</Container>;
}

const Container = styled.div`
    width: 100%;
    max-width: 1000px; 
    display: flex;
    justify-content: space-between;
    gap: 24px;
    padding: 24px;
    background-color: ${colors.background};
    border-radius: 12px;
    box-sizing: border-box;
`;
